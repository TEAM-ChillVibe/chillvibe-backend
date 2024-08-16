package com.chillvibe.chillvibe.global.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Uploader {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  // 파일 업로드
  public String upload(MultipartFile multipartFile, String dirName) throws IOException {

    // 업로드된 파일의 원래 이름 가져오기
    String originalFileName = multipartFile.getOriginalFilename();
    // 파일 이름 충돌을 피하기 위해 UUID 생성
    String uuid = UUID.randomUUID().toString();
    // 공백을 '_'로 대체하고 UUID를 추가한 고유 파일 이름 생성
    String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");
    // 업로드할 파일의 전체 경로 생성
    String fileName = dirName + "/" + uniqueFileName;

    log.info("fileName: " + fileName);

    // 'MultiPartFile'을 'File' 객체로 변환
    File uploadFile = convert(multipartFile);
    // 변환된 파일을 S3에 업로드
    String uploadImageUrl = putS3(uploadFile, fileName);
    // 로컬 파일 시스템에서 임시 파일을 삭제
    removeNewFile(uploadFile);
    return uploadImageUrl;
  }

  // 파일 S3 업로드 오버로딩
  public String upload(InputStream inputStream, String originalFileName, String dirName) throws IOException {
    String uuid = UUID.randomUUID().toString();
    String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");
    String fileName = dirName + "/" + uniqueFileName;

    log.info("fileName: " + fileName);

    ObjectMetadata metadata = new ObjectMetadata();
    byte[] bytes = IOUtils.toByteArray(inputStream);
    metadata.setContentLength(bytes.length);
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

    amazonS3.putObject(new PutObjectRequest(bucket, fileName, byteArrayInputStream, metadata)
        .withCannedAcl(CannedAccessControlList.PublicRead));

    return amazonS3.getUrl(bucket, fileName).toString();
  }

  // 파일 변환
  private File convert(MultipartFile file) throws IOException {
    String originalFileName = file.getOriginalFilename();
    String uuid = UUID.randomUUID().toString();
    String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");

    File convertFile = new File(uniqueFileName);
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      } catch (IOException e) {
        log.error("파일 변환 중 오류 발생: {}", e.getMessage());
        throw e;
      }
      return convertFile;
    }
    throw new IllegalArgumentException(String.format("파일 변환에 실패했습니다. %s", originalFileName));
  }

  // 파일 업로드 및 URL 반환
  private String putS3(File uploadFile, String fileName) {

    // S3에 파일을 업로드하고 URL 반환
    // 업로드된 파일을 공개적으로 읽을 수 있도록 설정
    amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
        .withCannedAcl(CannedAccessControlList.PublicRead));
    return amazonS3.getUrl(bucket, fileName).toString();
  }

  // File 객체로 담아둔 임시 파일 로컬 디스크에서 삭제
  private void removeNewFile(File targetFile) {

    // 확인 로그
    if (targetFile.delete()) {
      log.info("파일이 삭제되었습니다.");
    } else {
      log.info("파일이 삭제되지 못했습니다.");
    }
  }

  // S3에서 파일 삭제
  public void deleteFile(String fileUrl) {
    try {
      // 파일 URL에서 S3의 키를 추출
      String decodedFileName = URLDecoder.decode(fileUrl, "UTF-8");

      // S3의 파일 경로만 추출
      String fileName = decodedFileName.substring(decodedFileName.indexOf("/", decodedFileName.indexOf(".com") + 4) + 1);

      log.info("Deleting file from S3: " + fileName);
      amazonS3.deleteObject(bucket, fileName);
    } catch (UnsupportedEncodingException e) {
      log.error("Error while decoding the file name: {}", e.getMessage());
    } catch (Exception e) {
      log.error("Error while deleting the file from S3: {}", e.getMessage());
    }
  }

  // 파일 업데이트
  public String updateFile(MultipartFile newFile, String oldFileName, String dirName) throws IOException {
    log.info("S3 oldFileName: " + oldFileName);
    deleteFile(oldFileName);
    return upload(newFile, dirName);
  }

}
package com.chillvibe.chillvibe.global.common;

import com.chillvibe.chillvibe.global.s3.service.S3Uploader;
import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class ThumbnailGenerator {

  // Spotify에서 가져오는 사진은 640x640
  // 격자형태 최대한 사진이 깨지지 않게 1280으로 설정.
  private static final int THUMBNAIL_SIZE = 1280; // 썸네일 크기
  private static final String DEFAULT_IMAGE_PATH = "static/images/default_playlist.png"; // 기본 이미지 경로
  private BufferedImage defaultImage; // 기본 이미지를 저장할 변수
  @Autowired
  private S3Uploader s3Uploader; // AWS S3에 이미지를 업로드하기 위한 객체

  // 빈 생성 직후 실행 - 기본 이미지 로드하고 실패시 로깅
  @PostConstruct
  public void init() {
    try {
      defaultImage = ImageIO.read(new ClassPathResource(DEFAULT_IMAGE_PATH).getInputStream());
    } catch (IOException e) {
      throw new RuntimeException("플레이리스트 기본 이미지를 로딩하는데 실패했습니다.", e);
    }
  }

  public String updatePlaylistThumbnail(List<String> albumArtUrls, Long playlistId) throws IOException {
    return generateAndUploadThumbnail(albumArtUrls, "playlists", playlistId);
  }

  public String generateAndUploadThumbnail(List<String> albumArtUrls, String dirName, Long playlistId) throws IOException {
    BufferedImage thumbnail = generateThumbnail(albumArtUrls);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(thumbnail, "jpg", baos);
    byte[] imageBytes = baos.toByteArray();

    InputStream inputStream = new ByteArrayInputStream(imageBytes);
    String originalFileName = "thumbnail.jpg";
    return s3Uploader.upload(inputStream, originalFileName, dirName);
  }

  // 트랙 수에 따라 적절한 썸네일 생성 메서드 호출
  private BufferedImage generateThumbnail(List<String> albumArtUrls) throws IOException {
    BufferedImage result = new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE,
        BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = result.createGraphics();

    try {
      int trackCount = albumArtUrls.size();
      if (trackCount == 0) {
        drawDefaultImage(g2d);
      } else if (trackCount <= 3) {
        drawSingleImage(g2d, albumArtUrls.get(0));
      } else {
        drawFourImages(g2d, albumArtUrls);
      }
    } finally {
      g2d.dispose(); // 그래픽 리소스 해제
    }

    return result;
  }

  // 기본 이미지 그리기. 없을 경우 회색 배경에 Playlist 텍스트를 그린다.
  private void drawDefaultImage(Graphics2D g2d) {
    if (defaultImage != null) {
      g2d.drawImage(defaultImage, 0, 0, THUMBNAIL_SIZE, THUMBNAIL_SIZE, null);
    } else {
      g2d.setColor(Color.GRAY);
      g2d.fillRect(0, 0, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
      g2d.setColor(Color.WHITE);
      g2d.setFont(new Font("Arial", Font.BOLD, 24));
      g2d.drawString("Playlist", 10, THUMBNAIL_SIZE / 2);
    }
  }

  // 단일 이미지를 로드하고 그린다. 실패 시 기본 이미지를 그린다.
  private void drawSingleImage(Graphics2D g2d, String imageUrl) throws IOException {
    try {
      BufferedImage img = ImageIO.read(new URL(imageUrl));
      g2d.drawImage(img, 0, 0, THUMBNAIL_SIZE, THUMBNAIL_SIZE, null);
    } catch (IOException e) {
      log.error("Failed to load image: {}", imageUrl, e);
      drawDefaultImage(g2d);
    }
  }

  private void drawFourImages(Graphics2D g2d, List<String> imageUrls) throws IOException {
    for (int i = 0; i < Math.min(4, imageUrls.size()); i++) {
      try {
        BufferedImage img = ImageIO.read(new URL(imageUrls.get(i)));
        if (img != null) {
          int x = (i % 2) * (THUMBNAIL_SIZE / 2);
          int y = (i / 2) * (THUMBNAIL_SIZE / 2);
          g2d.drawImage(img, x, y, THUMBNAIL_SIZE / 2, THUMBNAIL_SIZE / 2, null);
        } else {
          throw new IOException("이미지를 읽는데 실패했습니다.");
        }
      } catch (IOException e) {
        log.error("이미지 로드 실패 (인덱스 {}): {}", i, imageUrls.get(i), e);
        // 실패한 이미지 영역을 회색으로 채우기
        int x = (i % 2) * (THUMBNAIL_SIZE / 2);
        int y = (i / 2) * (THUMBNAIL_SIZE / 2);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(x, y, THUMBNAIL_SIZE / 2, THUMBNAIL_SIZE / 2);
      }
    }
  }
}
package com.chillvibe.chillvibe.global.common;

import com.chillvibe.chillvibe.global.s3.service.S3Uploader;
import jakarta.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ThumbnailGenerator {

  // Spotify에서 가져오는 사진은 640x640
  // 격자형태 최대한 사진이 깨지지 않게 1280으로 설정.
  private static final int THUMBNAIL_SIZE = 1280;
  private static final String DEFAULT_IMAGE_PATH = "static/images/default_playlist.png";

  private BufferedImage defaultImage;

  @Autowired
  private S3Uploader s3Uploader;

  @PostConstruct
  public void init() {
    try {
      defaultImage = ImageIO.read(new ClassPathResource(DEFAULT_IMAGE_PATH).getInputStream());
    } catch (IOException e) {
      throw new RuntimeException("Failed to load default image", e);
    }
  }

  public String updatePlaylistThumbnail(List<String> albumArtUrls, Long playlistId) throws IOException {
    return generateAndUploadThumbnail(albumArtUrls, "playlists", playlistId);
  }

  public String generateAndUploadThumbnail(List<String> albumArtUrls, String dirName,
      Long playlistId) throws IOException {
    BufferedImage thumbnail = generateThumbnail(albumArtUrls);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(thumbnail, "jpg", baos);
    byte[] imageBytes = baos.toByteArray();

    String fileName = String.format("playlist_%d_thumbnail.jpg", playlistId);
    return s3Uploader.uploadOrReplace(imageBytes, dirName, fileName);
  }

  private BufferedImage generateThumbnail(List<String> albumArtUrls) throws IOException {
    int trackCount = albumArtUrls.size();
    BufferedImage result = new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE,
        BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = result.createGraphics();

    if (trackCount == 0) {
      drawDefaultImage(g2d);
    } else if (trackCount >= 1 && trackCount <= 3) {
      drawSingleImage(g2d, albumArtUrls.get(0));
    } else {
      drawFourImages(g2d, albumArtUrls);
    }

    g2d.dispose();
    return result;
  }

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

  private void drawSingleImage(Graphics2D g2d, String imageUrl) throws IOException {
    BufferedImage img = ImageIO.read(new URL(imageUrl));
    g2d.drawImage(img, 0, 0, THUMBNAIL_SIZE, THUMBNAIL_SIZE, null);
  }

  private void drawFourImages(Graphics2D g2d, List<String> imageUrls) throws IOException {
    for (int i = 0; i < Math.min(4, imageUrls.size()); i++) {
      BufferedImage img = ImageIO.read(new URL(imageUrls.get(i)));
      int x = (i % 2) * (THUMBNAIL_SIZE / 2);
      int y = (i / 2) * (THUMBNAIL_SIZE / 2);
      g2d.drawImage(img, x, y, THUMBNAIL_SIZE / 2, THUMBNAIL_SIZE / 2, null);
    }
  }
}
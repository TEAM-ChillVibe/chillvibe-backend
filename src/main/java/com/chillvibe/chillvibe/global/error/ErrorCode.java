package com.chillvibe.chillvibe.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.boot.logging.LogLevel;

/**
 * 에러 코드 enum
 * - 애플리케이션에서 발생할 수 있는 다양한 에러를 코드와 메시지로 정의합니다.
 * - 각 에러 코드는 상태 코드, 코드 값(도메인 별 넘버링), 메시지, 로그 레벨을 포함합니다.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

  // COMMON
  INVALID_INPUT_VALUE(400, "C001", "Invalid input value", LogLevel.ERROR),
  METHOD_NOT_ALLOWED(405, "C002", "Method not allowed", LogLevel.ERROR),
  NO_HANDLER_FOUND(404, "C003", "No handler found", LogLevel.ERROR),
  NO_RESOURCE_FOUND(404, "C004", "Resource not found", LogLevel.ERROR),
  HANDLE_ACCESS_DENIED(403, "C005", "Access denied", LogLevel.ERROR),
  INTERNAL_SERVER_ERROR(500, "C006", "Internal server error", LogLevel.ERROR),
  INVALID_TYPE_VALUE(400, "C007", "Invalid Type Value", LogLevel.ERROR),
  UNAUTHORIZED_ACCESS(403, "C008", "user id mismatch", LogLevel.ERROR),
  POSITIVE_VALUE_REQUIRED(400, "C009", "Value must be positive", LogLevel.ERROR),

  // COMMENT
  COMMENT_NOT_FOUND(404, "C101", "Comment not found", LogLevel.ERROR),
  USER_COMMENT_NOT_FOUND(404, "C102", "User comment not found", LogLevel.ERROR),
  POST_COMMENT_NOT_FOUND(404, "C103", "Post comment not found", LogLevel.ERROR),

  // HASHTAG
  HASHTAG_NOT_FOUND(404, "H001", "Hashtag not found", LogLevel.ERROR),
  USER_HASHTAG_NOT_FOUND(404, "H002", "User hashtag not found", LogLevel.ERROR),
  POST_HASHTAG_NOT_FOUND(404, "H003", "Post hashtag not found", LogLevel.ERROR),

  // PLAYLIST
  PLAYLIST_NOT_FOUND(404, "PL001", "Playlist not found", LogLevel.ERROR),

  // POST
  POST_NOT_FOUND(404, "p001", "Post not found", LogLevel.ERROR),
  USER_POST_NOT_FOUND(404, "p002", "User post not found", LogLevel.ERROR),
  UNLIKE_POST_ERROR(400, "p003", "Users haven't liked this post", LogLevel.ERROR),
  LIKE_POST_ERROR(400, "p004", "User already likes this post", LogLevel.ERROR),

  // TRACK
  TRACK_NOT_FOUND(404, "T001", "Track not found", LogLevel.ERROR),

  // SPOTIFY
  SPOTIFY_API_ERROR(500, "S001", "Spotify API error occurred, Try Again", LogLevel.ERROR),
  SPOTIFY_TOKEN_REFRESH_ERROR(500, "S002", "Failed to refresh Spotify access token", LogLevel.ERROR),
  SPOTIFY_TOKEN_INITIALIZATION_ERROR(500, "S003", "Failed to initialize Spotify access token", LogLevel.ERROR),

  // USER
  USER_NOT_FOUND(404, "U001", "User not found", LogLevel.ERROR),
  DUPLICATE_EMAIL(409, "U003", "Email is already registered", LogLevel.WARN),

  // SECURE
  UNAUTHENTICATED(401, "SC001", "User not authenticated", LogLevel.WARN),

  ;

  private final String code;
  private final String message;
  private int status;
  private final LogLevel logLevel;

  ErrorCode(final int status, final String code, final String message, LogLevel logLevel) {
    this.status = status;
    this.message = message;
    this.code = code;
    this.logLevel = logLevel;
  }

  public String getMessage() {
    return this.message;
  }

  public String getCode() {
    return code;
  }

  public int getStatus() {
    return status;
  }

  public LogLevel getLogLevel() {
    return logLevel;
  }
}

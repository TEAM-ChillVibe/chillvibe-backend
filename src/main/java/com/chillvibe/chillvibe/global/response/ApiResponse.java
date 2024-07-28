package com.chillvibe.chillvibe.global.response;

import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.ErrorMessage;

/**
 * API 응답 클래스
 * - 성공적인 응답과 에러 응답을 포함합니다.
 * - 제네릭 타입을 사용하여 데이터 타입을 유연하게 처리합니다.
 *
 * @param <T> 응답 데이터의 타입
 */
public class ApiResponse<T> {

  private final ErrorMessage message;

  private final T data;

  public ApiResponse(ErrorMessage message, T data) {
    this.message = message;
    this.data = data;
  }

  public static ApiResponse<?> success() {
    return new ApiResponse<>(null, null);
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<T>(null, data);
  }

  public static ApiResponse<?> error(ErrorCode code) {
    return new ApiResponse<>(new ErrorMessage(code), null);
  }

  public static ApiResponse<?> error(ErrorCode code, Object errorData) {
    return new ApiResponse<>(new ErrorMessage(code, errorData), null);
  }

  public ErrorMessage getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }
}

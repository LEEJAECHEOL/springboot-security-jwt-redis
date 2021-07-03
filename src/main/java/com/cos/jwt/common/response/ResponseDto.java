package com.cos.jwt.common.response;

import com.cos.jwt.common.error.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto<T> {
  private int status;
  private String message;
  private T data;

  public ResponseDto(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public ResponseDto(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }
  public ResponseDto(ErrorCode errorCode){
    this.status = errorCode.getStatus();
    this.message = errorCode.getMessage();
  }

}

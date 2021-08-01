package com.cos.jwt.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

  INVALID_REQUEST(400,"C004001","잘못된 요청입니다."),
  NOT_FOUND_RESOURCE(404,"COO4002","해당 리소스를 찾을 수 없습니다."),
  FORBIDDEN(403,"C004003","해당 권한이 없습니다."),
  SERVER_ERROR(500,"COO5001","서버에서의 오류입니다."),

  // Auth
  INVALID_EMAIL_FORMAT(400, "A004001", "올바른 이메일 형식이 아닙니다."),
  NOT_FOUND_USERNAME(400, "A004002", "아이디 또는 비밀번호를 확인해주세요."),
  EXIST_USERNAME(400, "A004003", "이미 사용 중인 아이디입니다."),
  NOT_FOUND_REFRESH_TOKEN(400, "A004004", "RefreshToken을 찾을 수 없습니다."),
  EXPIRED_TOKEN(401, "A004005", "만료된 토큰입니다."),
  INVALID_SIGNATURE_VERIFICATION(401, "A004006", "잘못된 토큰 서명입니다."),

  // Book
  NOT_FOUND_BOOK(400, "B004001", "해당 책을 찾을 수 없습니다.")


  ;

  private int status;
  private String code;
  private String message;

  ErrorCode(int status, String code, String message){
    this.status = status;
    this.code = code;
    this.message = message;
  }
}

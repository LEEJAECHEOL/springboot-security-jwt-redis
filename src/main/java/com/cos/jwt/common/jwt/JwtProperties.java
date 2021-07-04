package com.cos.jwt.common.jwt;

public interface JwtProperties {
  String SECRET_KEY = "jwt-key"; // 우리 서버만 알고 있는 비밀값
  String TOKEN_PREFIX = "Bearer ";
  String HEADER_STRING = "Authorization";
  String REFRESH_TOKEN_NAME = "refreshToken";

  long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
  long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

}

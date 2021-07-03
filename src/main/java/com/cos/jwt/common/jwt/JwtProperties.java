package com.cos.jwt.common.jwt;

public interface JwtProperties {
  String SECRET_KEY = "jwt-key"; // 우리 서버만 알고 있는 비밀값
  int EXPIRATION_TIME = (60000) * 600;  //(1분) * 10 = 10분
  String TOKEN_PREFIX = "Bearer ";
  String HEADER_STRING = "Authorization";
  String ACCESS_TOKEN_NAME = "accessToken";
  String REFRESH_TOKEN_NAME = "refreshToken";
}

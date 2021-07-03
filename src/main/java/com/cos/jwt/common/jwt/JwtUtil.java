package com.cos.jwt.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.common.auth.PrincipalDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
  private final static String SECRET_KEY = "jwt-key"; // 우리 서버만 알고 있는 비밀값
  private final static String TOKEN_PREFIX = "Bearer ";
  private final static String HEADER_STRING = "Authorization";
  private final static String ACCESS_TOKEN_NAME = "accessToken";
  private final static String REFRESH_TOKEN_NAME = "refreshToken";

  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

  public String generateToken(PrincipalDetails principalDetails) {
    return doGenerateToken(principalDetails, ACCESS_TOKEN_EXPIRE_TIME);
  }

  public String generateRefreshToken(PrincipalDetails principalDetails) {
    return doGenerateToken(principalDetails, REFRESH_TOKEN_EXPIRE_TIME);
  }

  private String doGenerateToken(PrincipalDetails principalDetails, long expireTime) {
    return JWT.create()
      .withSubject("instaToken")
      .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
      .withClaim("id", principalDetails.getUser().getId())
      .withClaim("username", principalDetails.getUser().getUsername())
      .sign(Algorithm.HMAC512(SECRET_KEY));
  }
}

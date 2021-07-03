package com.cos.jwt.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.common.error.ErrorCode;
import com.cos.jwt.common.error.exception.BusinessException;

import java.util.Date;

public class JwtUtil {
  private final static String SECRET_KEY = "jwt-key"; // 우리 서버만 알고 있는 비밀값
  public final static String TOKEN_PREFIX = "Bearer ";
  public final static String HEADER_STRING = "Authorization";
  public final static String REFRESH_TOKEN_NAME = "refreshToken";

  public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
  public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

  public static String generateToken(User user) {
    return doGenerateToken(user, ACCESS_TOKEN_EXPIRE_TIME);
  }

  public static String generateRefreshToken(User user) {
    return doGenerateToken(user, REFRESH_TOKEN_EXPIRE_TIME);
  }

  private static String doGenerateToken(User user, long expireTime) {
    return JWT.create()
      .withSubject("jwtToken")
      .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
      .withClaim("id", user.getId())
      .withClaim("username", user.getUsername())
      .sign(Algorithm.HMAC512(SECRET_KEY));
  }

  public static String getUsername(String token) {
    try {
      return JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token).getClaim("username").asString();
    }catch (SignatureVerificationException s){
      throw new BusinessException(ErrorCode.INVALID_SIGNATURE_VERIFICATION);
    }catch (TokenExpiredException t){
      throw new BusinessException(ErrorCode.EXPIRED_TOKEN);
    }
  }

}

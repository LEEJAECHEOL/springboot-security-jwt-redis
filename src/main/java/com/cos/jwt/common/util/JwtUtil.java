package com.cos.jwt.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.common.jwt.JwtProperties;
import java.util.Date;

public class JwtUtil {

  public static String generateToken(User user) {
    return doGenerateToken(user, JwtProperties.ACCESS_TOKEN_EXPIRE_TIME);
  }

  public static String generateRefreshToken(User user) {
    return doGenerateToken(user, JwtProperties.REFRESH_TOKEN_EXPIRE_TIME);
  }

  private static String doGenerateToken(User user, long expireTime) {
    return JWT.create()
              .withSubject("jwtToken")
              .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
              .withClaim("id", user.getId())
              .withClaim("username", user.getUsername())
              .sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));
  }

  public static String getUsernameFromToken(String token) {
    return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET_KEY)).build().verify(token).getClaim("username").asString();
  }

}

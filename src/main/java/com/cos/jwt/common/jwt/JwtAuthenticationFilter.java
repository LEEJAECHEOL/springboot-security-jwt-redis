package com.cos.jwt.common.jwt;

import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.common.auth.PrincipalDetails;
import com.cos.jwt.common.util.CookieUtil;
import com.cos.jwt.common.util.JwtUtil;
import com.cos.jwt.common.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  private final RedisUtil redisUtil;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil) {
    this.authenticationManager = authenticationManager;
    this.redisUtil = redisUtil;
  }

  // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수.
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    User user = getUser(request);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

    return authenticationManager.authenticate(authenticationToken);
  }


  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                          Authentication authResult) {

    PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();

    String jwtToken = JwtUtil.generateToken(principalDetails.getUser());
    String refreshToken = JwtUtil.generateRefreshToken(principalDetails.getUser());
    redisUtil.setDataExpire(refreshToken, principalDetails.getUsername(), JwtUtil.REFRESH_TOKEN_EXPIRE_TIME);

    Cookie cookie = CookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshToken);

    response.addHeader(JwtUtil.HEADER_STRING, JwtUtil.TOKEN_PREFIX + jwtToken);
    response.addCookie(cookie);

  }

  private User getUser(HttpServletRequest request) {
    ObjectMapper om = new ObjectMapper();
    User user = null;
    try {
      user = om.readValue(request.getInputStream(), User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return user;
  }
}

package com.cos.jwt.common.jwt;

import com.cos.jwt.common.util.CookieUtil;
import com.cos.jwt.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class JwtLogoutFilter implements LogoutHandler {
  private final RedisUtil redisUtil;

  @Override
  public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
    Cookie refreshCookie = CookieUtil.getCookie(JwtProperties.REFRESH_TOKEN_NAME);
    if(refreshCookie != null){
      String refreshToken = refreshCookie.getValue();
      // 1. refresh 토큰 제거
      redisUtil.deleteData(refreshToken);
      // 2. 쿠키 제거
      refreshCookie = CookieUtil.removeCookie(refreshCookie);
      httpServletResponse.addCookie(refreshCookie);
    }

  }


}

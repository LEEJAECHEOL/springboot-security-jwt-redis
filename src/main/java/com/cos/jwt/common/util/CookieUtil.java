package com.cos.jwt.common.util;

import com.cos.jwt.common.jwt.JwtProperties;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

  public static Cookie createCookie(String cookieName, String value){
    Cookie cookie = new Cookie(cookieName, value);
    cookie.setHttpOnly(true);
    cookie.setMaxAge((int) JwtProperties.REFRESH_TOKEN_EXPIRE_TIME / 1000);
    cookie.setPath("/");
    return cookie;
  }

  public static Cookie updateCookie(Cookie cookie, String value){
    cookie.setValue(value);
    cookie.setHttpOnly(true);
    cookie.setMaxAge((int) JwtProperties.REFRESH_TOKEN_EXPIRE_TIME / 1000);
    cookie.setPath("/");
    return cookie;
  }

  public static Cookie removeCookie(Cookie cookie){
    cookie.setHttpOnly(true);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    return cookie;
  }

  public static Cookie getCookie(String cookieName){
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    final Cookie[] cookies = request.getCookies();
    if(cookies == null)
      return null;
    for(Cookie cookie : cookies){
      if(cookie.getName().equals(cookieName))
        return cookie;
    }
    return null;
  }

}

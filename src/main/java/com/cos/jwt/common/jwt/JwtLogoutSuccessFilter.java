package com.cos.jwt.common.jwt;

import com.cos.jwt.common.error.ErrorCode;
import com.cos.jwt.common.error.exception.BusinessException;
import com.cos.jwt.common.util.CookieUtil;
import com.cos.jwt.common.util.JwtUtil;
import com.cos.jwt.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtLogoutSuccessFilter implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
    httpServletResponse.setStatus(HttpStatus.OK.value());
    httpServletResponse.sendRedirect("/");
  }
}

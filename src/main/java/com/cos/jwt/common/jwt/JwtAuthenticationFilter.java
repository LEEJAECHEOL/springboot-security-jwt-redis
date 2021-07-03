package com.cos.jwt.common.jwt;

import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.common.auth.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;
  private JwtUtil jwtUtil;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    super(authenticationManager);
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
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

    String jwtToken = jwtUtil.generateToken(principalDetails);

    response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
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

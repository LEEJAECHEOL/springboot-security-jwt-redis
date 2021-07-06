package com.cos.jwt.common.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cos.jwt.business.user.application.UserRepository;
import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.common.auth.PrincipalDetails;
import com.cos.jwt.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
  private UserRepository userRepository;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
    super(authenticationManager);
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

    if(jwtHeader == null || jwtHeader.startsWith("bearer")) {
      chain.doFilter(request, response);
      return;
    }

    String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
    String username = "";

    try{
      username = JwtUtil.getUsernameFromToken(jwtToken);
    }catch (SignatureVerificationException s){
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "잘못된 토큰 서명입니다.");
      return;
    }catch (TokenExpiredException t){
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 토큰입니다.");
      return;
    }

    if(username != null) {
      User userEntity = userRepository.findByUsername(username).get();
      PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
      Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
  }

}

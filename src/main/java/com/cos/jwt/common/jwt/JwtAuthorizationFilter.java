package com.cos.jwt.common.jwt;

import com.cos.jwt.business.user.application.UserRepository;
import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.common.auth.PrincipalDetails;
import com.cos.jwt.common.util.JwtUtil;
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

// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private UserRepository userRepository;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
    super(authenticationManager);
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws IOException, ServletException {

    String jwtHeader = request.getHeader(JwtUtil.HEADER_STRING);

    if(jwtHeader == null || jwtHeader.startsWith("bearer")) {
      chain.doFilter(request, response);
      return;
    }

    String jwtToken = request.getHeader(JwtUtil.HEADER_STRING).replace(JwtUtil.TOKEN_PREFIX, "");
    String username = JwtUtil.getUsername(jwtToken);

    if(username != null) {
      User userEntity = userRepository.findByUsername(username).get();
      PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
      Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
  }

}

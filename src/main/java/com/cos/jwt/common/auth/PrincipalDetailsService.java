package com.cos.jwt.common.auth;

import com.cos.jwt.business.user.application.UserRepository;
import com.cos.jwt.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new PrincipalDetails(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorCode.NOT_FOUND_USERNAME.getMessage())));
  }
}

package com.cos.jwt.business.auth.application;

import com.cos.jwt.business.user.application.UserRepository;
import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.common.error.ErrorCode;
import com.cos.jwt.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public void save(User user){
    String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
    user.encPassword(encPassword);
    userRepository.save(user);
  }

  public boolean findByUsername(String username){
    return userRepository.findByUsername(username).isPresent();
  }

}

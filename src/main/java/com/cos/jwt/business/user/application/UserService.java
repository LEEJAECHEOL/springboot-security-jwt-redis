package com.cos.jwt.business.user.application;

import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.common.error.ErrorCode;
import com.cos.jwt.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public User findByUsername(String username){
    return userRepository.findByUsername(username).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USERNAME));
  }

}

package com.cos.jwt.business.user.api;

import com.cos.jwt.business.user.application.UserService;
import com.cos.jwt.common.response.ApiResponse;
import com.cos.jwt.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/user")
  public ResponseEntity<ResponseDto> user(){
    return ApiResponse.set(new ResponseDto(HttpStatus.OK.value(), "test user"));
  }

  @GetMapping("/admin")
  public ResponseEntity<ResponseDto> admin(){
    return ApiResponse.set(new ResponseDto(HttpStatus.OK.value(), "test admin"));
  }
}

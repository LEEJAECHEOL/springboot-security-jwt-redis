package com.cos.jwt.business.auth.api;

import com.cos.jwt.business.auth.application.AuthService;
import com.cos.jwt.business.auth.form.AuthForm.*;
import com.cos.jwt.common.error.ErrorCode;
import com.cos.jwt.common.error.exception.BusinessException;
import com.cos.jwt.common.response.ApiResponse;
import com.cos.jwt.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/join")
  public ResponseEntity<ResponseDto> join(@Valid @RequestBody Request.Join join){
    authService.save(join.toEntity());
    return ApiResponse.set(new ResponseDto(HttpStatus.CREATED.value(), "회원가입을 완료하였습니다."));
  }

  @GetMapping("/checkUsername/{username}")
  public ResponseEntity<ResponseDto> checkUsername(@PathVariable String username){
    if(authService.findByUsername(username)){
      throw new BusinessException(ErrorCode.EXIST_USERNAME);
    }else{
      return ApiResponse.set(new ResponseDto(HttpStatus.OK.value(), "사용 가능한 아이디입니다."));
    }
  }
}

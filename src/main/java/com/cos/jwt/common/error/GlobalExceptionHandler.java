package com.cos.jwt.common.error;

import com.cos.jwt.common.error.exception.BusinessException;
import com.cos.jwt.common.response.ApiResponse;
import com.cos.jwt.common.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ResponseDto> businessException(BusinessException e) {
    ErrorCode errorCode = e.getErrorCode();
    System.out.println(errorCode.getMessage());
    return ApiResponse.set(new ResponseDto(errorCode));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException e){
    String message = e.getBindingResult().getFieldError().getDefaultMessage();
    return ApiResponse.set(new ResponseDto(HttpStatus.BAD_REQUEST.value(), message));
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<ResponseDto> nullPointerException(NullPointerException e){
    String message = e.getMessage();
    return ApiResponse.set(new ResponseDto(HttpStatus.BAD_REQUEST.value(), message));
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<ResponseDto> sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
    return ApiResponse.set(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "이미 사용 중인 아이디입니다."));
  }


}

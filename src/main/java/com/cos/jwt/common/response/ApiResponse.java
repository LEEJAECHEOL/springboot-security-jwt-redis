package com.cos.jwt.common.response;

import org.springframework.http.ResponseEntity;

public class ApiResponse {

  public static ResponseEntity<ResponseDto> set(ResponseDto responseDto){
    return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
  }
}

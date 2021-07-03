package com.cos.jwt.business.auth.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.business.auth.application.AuthService;
import com.cos.jwt.business.auth.form.AuthForm.*;
import com.cos.jwt.business.user.application.UserService;
import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.common.error.ErrorCode;
import com.cos.jwt.common.error.exception.BusinessException;
import com.cos.jwt.common.response.ApiResponse;
import com.cos.jwt.common.response.ResponseDto;
import com.cos.jwt.common.util.CookieUtil;
import com.cos.jwt.common.util.JwtUtil;
import com.cos.jwt.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final UserService userService;
  private final RedisUtil redisUtil;

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

  @PostMapping("/refresh")
  public ResponseEntity<ResponseDto> refresh(HttpServletResponse response){
    // 1. 쿠키에서 refresh 토큰 가져오기
    Cookie cookie = getRefreshCookie();
    String refreshToken = cookie.getValue();
    String redisUsername = redisUtil.getData(refreshToken);

    // 2. 토큰 검증
    String tokenUsername = JwtUtil.getUsername(refreshToken);

    // 3. 토큰의 username 동일한지 체크
    compareUsername(redisUsername, tokenUsername);

    // 4. 정보가져오기
    User user = userService.findByUsername(tokenUsername);

    // 5. accessToken, refreshToken 재생성
    String newJwtToken = JwtUtil.generateToken(user);
    String newRefreshToken = JwtUtil.generateRefreshToken(user);

    // 6. 기존 refresh토큰 지우기
    setNewRedis(refreshToken, user, newRefreshToken);

    // 7. 쿠키 값 변경 및 payload 값 변경
    setNewCookie(response, cookie, newJwtToken, newRefreshToken);

    return ApiResponse.set(new ResponseDto(HttpStatus.OK.value(), "정상적으로 처리되었습니다."));
  }

//  @PostMapping("/logout")
//  public ResponseEntity<ResponseDto> logout(HttpServletResponse response, HttpSession session){
//    System.out.println("is run?");
//    Cookie cookie = getRefreshCookie();
//    String refreshToken = cookie.getValue();
//    // 1. refresh 토큰 제거
//    redisUtil.deleteData(refreshToken);
//    // 2. 쿠키 제거
//    cookie = CookieUtil.removeCookie(cookie);
//    response.addCookie(cookie);
//    // 3. 세션 삭제
//    session.invalidate();
//    response.addHeader(JwtUtil.HEADER_STRING, "");
//    return ApiResponse.set(new ResponseDto(HttpStatus.OK.value(), "로그아웃 완료"));
//  }

  private void setNewRedis(String refreshToken, User user, String newRefreshToken) {
    redisUtil.deleteData(refreshToken);
    redisUtil.setDataExpire(newRefreshToken, user.getUsername(), JwtUtil.REFRESH_TOKEN_EXPIRE_TIME);
  }

  private void setNewCookie(HttpServletResponse response, Cookie cookie, String newJwtToken, String newRefreshToken) {
    cookie = CookieUtil.updateCookie(cookie, newRefreshToken);

    response.addHeader(JwtUtil.HEADER_STRING, JwtUtil.TOKEN_PREFIX + newJwtToken);
    response.addCookie(cookie);
  }

  private void compareUsername(String redisUsername, String tokenUsername) {
    if(!redisUsername.equals(tokenUsername)){
      throw new BusinessException(ErrorCode.INVALID_SIGNATURE_VERIFICATION);
    }
  }

  private Cookie getRefreshCookie() {
    Cookie refreshCookie = CookieUtil.getCookie(JwtUtil.REFRESH_TOKEN_NAME);
    if(refreshCookie == null){
      throw new BusinessException(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
    }else{
      return refreshCookie;
    }
  }

}

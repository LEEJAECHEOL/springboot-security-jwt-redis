package com.cos.jwt.business.auth.form;

import com.cos.jwt.business.user.entity.User;
import com.cos.jwt.business.user.enumerated.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthForm {

  public static class Request{
    @Getter
    @Setter
    public static class Join{
      @Email(message = "아이디는 이메일형식으로 입력해주세요.")
      @NotBlank(message = "아이디를 입력해주세요.")
      private String username;

      @NotBlank(message = "비밀번호를 입력해주세요.")
      private String password;

      @NotBlank(message = "이름을 입력해주세요.")
      private String name;

      public User toEntity(){
        return User.builder()
          .username(username)
          .password(password)
          .role(Role.USER)
          .build();
      }
    }
  }

  public static class Response{

  }
}

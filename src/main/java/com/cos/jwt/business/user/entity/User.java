package com.cos.jwt.business.user.entity;

import com.cos.jwt.business.user.enumerated.Role;
import com.cos.jwt.common.entity.BasicEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String username;
  @Column(nullable = false)
  private String password;

  @Column(nullable = true)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(length = 10, columnDefinition = "varchar(10) default 'USER'")
  private Role role;

  @Builder
  public User(Long id, String username, String password, String email, Role role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
  }

  public void encPassword(String password){
    this.password = password;
  }

}

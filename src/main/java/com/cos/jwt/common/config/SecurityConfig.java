package com.cos.jwt.common.config;

import com.cos.jwt.business.user.application.UserRepository;
import com.cos.jwt.common.jwt.JwtAuthenticationFilter;
import com.cos.jwt.common.jwt.JwtAuthorizationFilter;
import com.cos.jwt.common.jwt.JwtLogoutFilter;
import com.cos.jwt.common.jwt.JwtLogoutSuccessFilter;
import com.cos.jwt.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CorsConfig corsConfig;
  private final UserRepository userRepository;
  private final RedisUtil redisUtil;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .addFilter(corsConfig.corsFilter())
      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .formLogin().disable()
      .httpBasic().disable()
      .addFilter(new JwtAuthenticationFilter(authenticationManager(),redisUtil)) // AuthenticationManager
      .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
      .authorizeRequests()
			.antMatchers("/user/**")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/admin/**")
				.access("hasRole('ROLE_ADMIN')")
      .anyRequest().permitAll()
      .and()
      .logout()
      .addLogoutHandler(new JwtLogoutFilter(redisUtil))
      .logoutSuccessHandler(new JwtLogoutSuccessFilter())
      .invalidateHttpSession(true)
    ;
  }
}

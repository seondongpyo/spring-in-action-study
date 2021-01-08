package com.example.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**")
                .access("permitAll")
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 인메모리 기반
        /*
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("{noop}password1")
                .authorities("ROLE_USER")
                .and()
                .withUser("user2")
                .password("{noop}password2")
                .authorities("ROLE_USER");
         */

        // JDBC 기반
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                // 사용자 정보 쿼리의 커스터마이징
                // 스프링 시큐리티의 기본 데이터베이스 테이블과 달라도 되지만, 테이블이 갖는 열의 데이터 타입과 길이는 일치해야 한다
                .usersByUsernameQuery(
                    "select username, password, enabled from users " + // 사용자 정보 인증 쿼리에서는 username, password, enabled 열의 값을 반환해야 한다
                    "where username = ?") // 매개변수는 하나이며, username이어야 한다
                .authoritiesByUsernameQuery(
                    "select username, authority from authorities " + // 사용자 권한 쿼리에서는 해당 사용자 이름(username), 부여된 권한(authority)을 포함하는 다수의 행을 반환할 수 있다
                    "where username = ?")
                .passwordEncoder(new NoEncodingPasswordEncoder()); // 암호화를 사용하지 않는 비밀번호 인코더(임시)
    }
}

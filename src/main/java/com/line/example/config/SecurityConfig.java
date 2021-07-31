package com.line.example.config;

import com.line.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**", "/api/**", "/signup").permitAll()
                // 페이지 권한 설정
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/myinfo").hasRole("MEMBER")
//                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .exceptionHandling().accessDeniedPage()
            .formLogin()
                .loginPage("/login.html")
                .failureUrl("/login-error.html")
                .usernameParameter("name")
                .passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .and()
            .headers()
                .frameOptions().disable()
                .and()
            .csrf()
                .disable();
    }
}

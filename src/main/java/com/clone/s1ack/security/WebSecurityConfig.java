package com.clone.s1ack.security;

import com.clone.s1ack.security.jwt.AuthenticationEntryPointException;
import com.clone.s1ack.security.jwt.JwtAuthFilter;
import com.clone.s1ack.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity //시큐리티 활성화
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final AuthenticationEntryPointException authenticationEntryPointException;

    //password를 암호화 하지않으면 spring security가 접근을 허가하지 않는다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화 방식 BCrypt
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors().configurationSource(request -> {
           var cors = new CorsConfiguration();
           cors.setAllowedOriginPatterns(List.of("*"));
           cors.setAllowedMethods(List.of("*"));
           cors.setAllowedHeaders(List.of("*"));
           cors.addExposedHeader("Access_Token");
           cors.addExposedHeader("Refresh_Token");
           cors.setAllowCredentials(true);
           return cors;
        });

        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointException);


        http.formLogin().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/api/signup").permitAll()
                .antMatchers( "/api/login").permitAll()
                .antMatchers("/api/logout").permitAll()

                .antMatchers(HttpMethod.GET, "/product/**").permitAll()
                .antMatchers(HttpMethod.GET, "/product/{productId}/comment/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

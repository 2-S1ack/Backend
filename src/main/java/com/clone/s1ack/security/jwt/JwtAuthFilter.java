package com.clone.s1ack.security.jwt;

import com.clone.s1ack.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    // OncePerRequestFilter

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");


        /**
         * 토큰 인증 + 검증
         */
        if(accessToken != null) {
            // Access 토큰 이 유효한지
            if (!jwtUtil.tokenValidation(accessToken)) {
                log.info("====== JwtAuthFilter.doFilterInternal.accessToken.tokenValidation == false =====");
                jwtExceptionHandler(response);
                return;
            }
            // 토큰 검증 완료
            // SecurityContext 에 인증 객체인 Authentication 를 넣는다.
            setAuthentication(jwtUtil.getEmailFromToken(accessToken));
        } else if(refreshToken != null) { //
            // refresh 토큰 이 유효한지
            if(!jwtUtil.tokenValidation(refreshToken)) {
                log.info("====== JwtAuthFilter.doFilterInternal.refreshToken.tokenValidation == false =====");
                jwtExceptionHandler(response);
                return;
            }
            setAuthentication(jwtUtil.getEmailFromToken(refreshToken));
        }
        // 다음 필터 호출
        filterChain.doFilter(request, response);
    }


    public void setAuthentication(String username) {
        // Authentication 를 SecurityContext 에다가 넣는다.
        // WebSecurityConfig에 UsernamePasswordAuthentivcationFilter 전에
        // JwtAuthFilter 가 실행되록 하였는데 앞서 말한 필터가 실행될 때
        // SecurityContext 에 Authentication 이 있는지 확인하고 있다면 deny 를 하지 않는다.
        Authentication authentication = jwtUtil.createAuthentication(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            String json = new ObjectMapper().writeValueAsString(ResponseDto.fail(HttpStatus.UNAUTHORIZED.value(),null, "TOKEN이 만료되었습니다"));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

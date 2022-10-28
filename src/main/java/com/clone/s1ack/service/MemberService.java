package com.clone.s1ack.service;

import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.RefreshToken;
import com.clone.s1ack.repository.MemberRepository;
import com.clone.s1ack.repository.RefreshTokenRepository;
import com.clone.s1ack.security.jwt.JwtUtil;
import com.clone.s1ack.security.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.clone.s1ack.dto.request.MemberRequestDto.*;
import static com.clone.s1ack.dto.response.MemberResponseDto.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    //회원가입
    @Transactional
    public MemberAuthResponseDto signup(MemberSignupRequestDto memberSignupRequestDto) {
        if(memberRepository.findByUsername(memberSignupRequestDto.getUsername()).isPresent()) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        if(memberRepository.findByEmail(memberSignupRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        if(!memberSignupRequestDto.getPassword().equals(memberSignupRequestDto.getPasswordConfirm())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }


        passwordEncode(memberSignupRequestDto);
        Member savedMember = new Member(memberSignupRequestDto);
        memberRepository.save(savedMember);

        return new MemberAuthResponseDto(savedMember);
    }

    private void passwordEncode(MemberSignupRequestDto memberSignupRequestDto) {
        String encodedPassword = passwordEncoder.encode(memberSignupRequestDto.getPassword());
        memberSignupRequestDto.setEncodePassword(encodedPassword);
    }

    @Transactional
    public MemberAuthResponseDto login(MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) {

        log.info("================");
        log.info("memberLoginRequestDto = {}", memberLoginRequestDto.getEmail());
        log.info("memberLoginRequestDto = {}", memberLoginRequestDto.getPassword());
        log.info("================");

        Member findMember = memberRepository.findByEmail(memberLoginRequestDto.getEmail()).orElseThrow(
                () -> new RuntimeException("해당 이메일이 존재하지 않습니다.")
        );

        if(!passwordEncoder.matches(memberLoginRequestDto.getPassword(), findMember.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(memberLoginRequestDto.getEmail());
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByEmail(findMember.getEmail());

        if(findRefreshToken.isPresent()) {
            refreshTokenRepository.save(findRefreshToken.get().updateToken(tokenDto.getRefreshToken()));
            System.out.println("========= true =======");
        } else { // Refresh Token이 없는 경우
            RefreshToken newRefreshToken = new RefreshToken(tokenDto.getRefreshToken(), findMember.getEmail());
            refreshTokenRepository.save(newRefreshToken);
            System.out.println("========= false =======");
        }
        // header 에 토큰을 담음
        addTokenHeader(response, tokenDto);

        return new MemberAuthResponseDto(findMember);
    }

    private void addTokenHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

//    public String logout(Member member) {
//        return "로그아웃 완료";
//    }
}


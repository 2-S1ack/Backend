package com.clone.s1ack.controller;

import com.clone.s1ack.dto.ResponseDto;
import com.clone.s1ack.dto.request.MemberRequestDto;
import com.clone.s1ack.dto.response.MemberResponseDto;
import com.clone.s1ack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseDto<MemberResponseDto.MemberAuthResponseDto> registerMember(@RequestBody @Valid MemberRequestDto.MemberSignupRequestDto memberRequestDto) {
        return ResponseDto.success(memberService.signup(memberRequestDto));
    }

    //로그인
    @PostMapping("/api/login")
    public ResponseDto<MemberResponseDto.MemberAuthResponseDto> login(@RequestBody MemberRequestDto.MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) {
        return ResponseDto.success(memberService.login(memberLoginRequestDto, response));
    }

//    //로그아웃
//    @PostMapping
//    public HttpHeaders setHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
//        return headers;
//    }
}

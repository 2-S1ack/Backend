package com.clone.s1ack.controller;

import com.clone.s1ack.dto.ResponseDto;
import com.clone.s1ack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.clone.s1ack.dto.request.MemberRequestDto.*;
import static com.clone.s1ack.dto.response.MemberResponseDto.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseDto<MemberAuthResponseDto> registerMember(@RequestBody @Valid MemberSignupRequestDto memberRequestDto) {
        return ResponseDto.success(memberService.signup(memberRequestDto));
    }

    //로그인
    @PostMapping("/api/login")
    public ResponseDto<MemberAuthResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) {
        return ResponseDto.success(memberService.login(memberLoginRequestDto, response));
    }

//    @PostMapping("/api/logout")
//    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        String message = memberService.logout(userDetails.getMember());
//        return new ResponseEntity<>(ResponseDto.success(message), HttpStatus.OK);
//    }
}

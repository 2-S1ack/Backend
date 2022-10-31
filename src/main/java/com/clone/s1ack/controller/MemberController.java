package com.clone.s1ack.controller;

import com.clone.s1ack.dto.ResponseDto;
import com.clone.s1ack.dto.response.MemberResponseDto;
import com.clone.s1ack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.clone.s1ack.dto.request.MemberRequestDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseDto<MemberResponseDto.MemberAuthResponseDto> registerMember(@RequestBody @Valid MemberSignupRequestDto memberRequestDto) {
        return ResponseDto.success(memberService.signup(memberRequestDto));
    }

    //로그인
    @PostMapping("/login")
    public ResponseDto<MemberResponseDto.MemberAuthResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) {
        return ResponseDto.success(memberService.login(memberLoginRequestDto, response));
    }

    /**
     * 이메일 중복 확인
     */
    @PostMapping("/member/duplication/email")
    public ResponseDto<String> dupEmail(@RequestBody MemberSignUpDuplicateEmailDto memberSignUpDuplicateEmailDto) {
        return memberService.isExistEmail(memberSignUpDuplicateEmailDto);
    }

    /**
     * 닉네임 중복 확인
     */
    @PostMapping("/member/duplication/username")
    public ResponseDto<String> dupUsername(@RequestBody MemberSignUpDuplicateUsernameDto memberSignUpDuplicateUsernameDto) {
        return memberService.isExistUsername(memberSignUpDuplicateUsernameDto);
    }

//    //로그아웃
//    @PostMapping
//    public HttpHeaders setHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
//        return headers;
//    }
}

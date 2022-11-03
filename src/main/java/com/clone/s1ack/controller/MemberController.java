package com.clone.s1ack.controller;

import com.clone.s1ack.dto.ResponseDto;
import com.clone.s1ack.security.user.UserDetailsImpl;
import com.clone.s1ack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static com.clone.s1ack.dto.request.MemberRequestDto.*;
import static com.clone.s1ack.dto.response.MemberResponseDto.MemberAuthResponseDto;
import static com.clone.s1ack.dto.response.MemberResponseDto.ProfileResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseDto<MemberAuthResponseDto> registerMember(@RequestBody @Valid MemberSignupRequestDto memberRequestDto) {
        return ResponseDto.success(memberService.signup(memberRequestDto));
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseDto<MemberAuthResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) {
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

    @PatchMapping("/member/profile")
    public ProfileResponseDto editProfile(@RequestParam(required = false, value = "file") MultipartFile multipartFile,
                                                @RequestParam(value = "name") MemberSignUpDuplicateUsernameDto memberSignUpDuplicateUsernameDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return memberService.modifiedProfile(multipartFile, memberSignUpDuplicateUsernameDto.getUsername(), userDetails.getUsername());
    }

//    //로그아웃
//    @PostMapping
//    public HttpHeaders setHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
//        return headers;
//    }
}

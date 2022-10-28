package com.clone.s1ack.controller;

import com.clone.s1ack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
//    @PostMapping("/signup")
//    public ResponseEntity<ResponseDto<MemberResponseDto>> registerMember(@RequestBody @Valid MemberRequestDto memberRequestDto) {
//        return memberService.signup(memberRequestDto);
//    }
//
//    //로그인
//    @PostMapping("/api/login")
//    public ResponseEntity<ResponseDto<MemberResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
//        return memberService.login(loginRequestDto, httpServletResponse);
//    }
//
//    @PostMapping("/api/logout")
//    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        String message = memberService.logout(userDetails.getMember());
//        return new ResponseEntity<>(ResponseDto.success(message), HttpStatus.OK);
//    }
//
//    //로그아웃
//    // not yey
//
//    @PostMapping
//    public HttpHeaders setHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
//        return headers;
//    }
}

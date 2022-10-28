package com.clone.s1ack.service;

import com.clone.s1ack.repository.MemberRepository;
import com.clone.s1ack.repository.RefreshTokenRepository;
import com.clone.s1ack.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;


//    //회원가입
//    @Transactional
//    public ResponseEntity<ResponseDto<MemberResponseDto>> signup(MemberRequestDto memberReqDto) {
//
//        // username 중복 검사
//        usernameDuplicateCheck(memberReqDto);
//
//        // 비빌번호 확인 & 비빌번호 불일치
//        if(!memberReqDto.getPassword().equals(memberReqDto.getPasswordConfirm())){
//            throw new GlobalException(ErrorCode.BAD_PASSWORD_CONFIRM);
//        }
//
//        Member member = Member.builder()
//                .username(memberReqDto.getUsername())
//                .password(passwordEncoder.encode(memberReqDto.getPassword()))
//                .build();
//        memberRepository.save(member);
//        return ResponseEntity.ok().body(ResponseDto.success(
//                MemberResponseDto.builder()
//                        .username(member.getUsername())
//                        .createdAt(member.getCreatedAt())
//                        .modifiedAt(member.getModifiedAt())
//                        .build()
//        ));
//    }
//
//    public void usernameDuplicateCheck(MemberRequestDto memberReqDto) {
//        if(memberRepository.findByUsername(memberReqDto.getUsername()).isPresent()){
//            throw new GlobalException(ErrorCode.DUPLICATE_MEMBER_ID);
//            // ex) return ResponseDto.fail()
//        }
//    }
//
//    //로그인
//    @Transactional
//    public ResponseEntity<ResponseDto<MemberResponseDto>> login(LoginRequestDto loginReqDto, HttpServletResponse response) {
//
//        Member member = check.isPresentMember(loginReqDto.getUsername());
//
//        //사용자가 있는지 확인
//        if(null == member){
//            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
//        }
//        //비밀번호가 맞는지 확인
//        if(!member.validatePassword(passwordEncoder, loginReqDto.getPassword())){
//            throw new GlobalException(ErrorCode.BAD_PASSWORD);
//        }
//
//        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getUsername());
//
//        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberUsername(loginReqDto.getUsername());
//
//        if(refreshToken.isPresent()) {
//            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
//        }else {
//            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getUsername());
//            refreshTokenRepository.save(newToken);
//        }
//        setHeader(response, tokenDto);
//
//        return ResponseEntity.ok().body(ResponseDto.success(
//                MemberResponseDto.builder()
//                        .username(member.getUsername())
//                        .createdAt(member.getCreatedAt())
//                        .modifiedAt(member.getModifiedAt())
//                        .build()
//        ));
//    }
//
//    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
//        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
//        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
//
//    }
//
//    /**
//     * 서버쪽에서는 토큰을 지워주는 것만 해주는게 맞는 것 같고
//     * 정상적인 응답을 받은 프론트에서 헤더를 지워주도록 하면 될 것 같습니다
//     * react: Storage.removeItem()
//     */
//    public String logout(Member member) {
//        refreshTokenRepository.deleteByMemberUsername(member.getUsername());
//        return "로그아웃 완료";
//    }
}


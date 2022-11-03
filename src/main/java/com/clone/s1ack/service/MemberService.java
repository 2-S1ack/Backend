package com.clone.s1ack.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.clone.s1ack.domain.Member;
import com.clone.s1ack.domain.RefreshToken;
import com.clone.s1ack.dto.ResponseDto;
import com.clone.s1ack.exception.CustomCommonException;
import com.clone.s1ack.exception.ErrorCode;
import com.clone.s1ack.repository.MemberRepository;
import com.clone.s1ack.repository.RefreshTokenRepository;
import com.clone.s1ack.security.jwt.JwtUtil;
import com.clone.s1ack.security.jwt.TokenDto;
import com.clone.s1ack.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static com.clone.s1ack.dto.request.MemberRequestDto.*;
import static com.clone.s1ack.dto.response.MemberResponseDto.*;
import static com.clone.s1ack.dto.response.MemberResponseDto.MemberAuthResponseDto;
import static com.clone.s1ack.exception.ErrorCode.DUPLICATE_USERNAME;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

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

    // 로그인
    @Transactional
    public MemberAuthResponseDto login(MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) {

        Member findMember = memberRepository.findByEmail(memberLoginRequestDto.getEmail()).orElseThrow(
                () -> new RuntimeException("해당 이메일은 존재하지 않습니다.")
        );

        if(!passwordEncoder.matches(memberLoginRequestDto.getPassword(), findMember.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(memberLoginRequestDto.getEmail());
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByEmail(findMember.getEmail());

        if(findRefreshToken.isPresent()) {
            refreshTokenRepository.save(findRefreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else { // Refresh Token이 없는 경우
            RefreshToken newRefreshToken = new RefreshToken(tokenDto.getRefreshToken(), findMember.getEmail());
            refreshTokenRepository.save(newRefreshToken);
        }
        addTokenHeader(response, tokenDto);

        return new MemberAuthResponseDto(findMember);
    }

    public ResponseDto<String> isExistEmail(MemberSignUpDuplicateEmailDto memberSignUpDuplicateEmailDto) {
        memberRepository.findByEmail(memberSignUpDuplicateEmailDto.getEmail()).ifPresent(member -> {
            throw new CustomCommonException(ErrorCode.DUPLICATE_USERNAME);
        });
        return ResponseDto.success("중복된 이메일이 존재하지 않습니다.");
    }

    public ResponseDto<String> isExistUsername(MemberSignUpDuplicateUsernameDto memberSignUpDuplicateUsernameDto) {
//        if(memberRepository.findByUsername(memberSignUpDuplicateUsernameDto.getUsername()).isPresent()) {
//            return ResponseDto.fail("중복된 닉네임이 존재합니다.", HttpStatus.FORBIDDEN);
//        }
        return ResponseDto.success("중복된 닉네임이 존재하지 않습니다.");
    }

    private void passwordEncode(MemberSignupRequestDto memberSignupRequestDto) {
        String encodedPassword = passwordEncoder.encode(memberSignupRequestDto.getPassword());
        memberSignupRequestDto.setEncodePassword(encodedPassword);
    }

    private void addTokenHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    @Transactional
    public ProfileResponseDto modifiedProfile(MultipartFile multipartFile, String name, String loginUser) throws IOException {
        log.info("loginUser = {}", loginUser);
        Member findMember = memberRepository.findByUsername(loginUser).orElseThrow(
                () -> new IllegalArgumentException("유효한 회원이 아닙니다"));

        String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());

        log.info("=============");
        log.info("fileName = {}", fileName);
        log.info("=============");

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
        objectMetadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

        amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        String imgUrl = amazonS3Client.getUrl(bucketName, fileName).toString();

        log.info("=============");
        log.info("imgUrl = {}", imgUrl);
        log.info("=============");

        //dirty Checking 기능활용
        findMember.updateMember(fileName, imgUrl, name);

        return new ProfileResponseDto(findMember.getUsername(), findMember.getUrl());
    }
}

//    public String logout(Member member) {
//        refreshTokenRepository.deleteByMemberUsername(member.getUsername());
//        return "로그아웃 완료";
//    }


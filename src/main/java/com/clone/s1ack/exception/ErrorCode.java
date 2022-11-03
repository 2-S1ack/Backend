package com.clone.s1ack.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //400 BAD_REQUEST 잘못된 요청
    DUPLICATE_EMAIL(400, HttpStatus.BAD_REQUEST, "중복된 이메일이 존재합니다."),
    DUPLICATE_USERNAME(400, HttpStatus.BAD_REQUEST, "중복된 닉네임이 존재합니다.");
//
//    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),
//    //404 NOT_FOUND 잘못된 리소스 접근
//    DISPLAY_NOT_FOUND(404, "존재하지 않는 전시회 ID 입니다."),
//    FAIR_NOT_FOUND(404, "존재하지 않는 박람회 ID 박람회입니다."),
//    FESTIVAL_NOT_FOUND(404, "존재하지 않는 페스티벌 ID 페스티벌입니다."),
//    SAVED_DISPLAY_NOT_FOUND(404, "저장하지 않은 전시회입니다."),
//    SAVED_FAIR_NOT_FOUND(404, "저장하지 않은 박람회입니다."),
//    SAVED_FESTIVAL_NOT_FOUND(404, "저장하지 않은 페스티벌입니다."),
//
//    //409 CONFLICT 중복된 리소스
//    ALREADY_SAVED_DISPLAY(409, "이미 저장한 전시회입니다."),
//    ALREADY_SAVED_FAIR(409, "이미 저장한 박람회입니다."),
//    ALREADY_SAVED_FESTIVAL(409, "이미 저장한 페스티벌입니다."),
//
//    //500 INTERNAL SERVER ERROR
//    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 서버 팀에 연락주세요!");

    private final int status;
    private final HttpStatus httpStatus;
    private final String message;
}

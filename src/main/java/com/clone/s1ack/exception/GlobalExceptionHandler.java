package com.clone.s1ack.exception;


import com.clone.s1ack.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 로그인이 필요합니다.
    // 권한이 없는 유저입니다.
    // 존재하지 않는 사용자입니다.
    // 존재하지 않는 방입니다.
    // 비밀번호가 일치하지 않습니다.
    // 이미 존재하는 이메일입니다.
    // 이미 존재하는 닉네임입니다.
    // 해당 이메일은 존재하지 않습니다.
    // 비밀번호가 일치하지 않습니다.

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ResponseDto<Object>> customIllegalArgumentException(CustomCommonException e) {
//        return new ResponseEntity<>(ResponseDto.fail(e.getStatus(), e.getHttpStatus(), e.getMessage()), e.getHttpStatus());
//    }

    @ExceptionHandler(CustomCommonException.class)
    public ResponseEntity<ResponseDto<Object>> customCommonException(CustomCommonException e) {
        return new ResponseEntity<>(ResponseDto.fail(e.getStatus(), e.getHttpStatus(), e.getMessage()),
                                     e.getHttpStatus());
    }

//    @ExceptionHandler({ Exception.class })
//    protected ResponseDto<Exception> handleServerException(Exception ex) {
//        return ResponseDto.fail(ex.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ErrorResult illegalExHandler(IllegalArgumentException e) {
//        log.error("[exceptionHandler] ex", e);
//        return new ErrorResult("BAD", e.getMessage());
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> runtimeException(RuntimeException e) {
//        log.error("[exceptionHandler] ex", e);
//        ErrorResult errorResult = new ErrorResult("BAD", e.getMessage());
//        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
//    }

}

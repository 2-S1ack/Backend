package com.clone.s1ack.exception;


import com.clone.s1ack.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ CustomException.class })
    protected ResponseDto<CustomException> handleCustomException(CustomException ex) {
        return ResponseDto.fail(ex.getErrorCode().getMessage());
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseDto<Exception> handleServerException(Exception ex) {
        return ResponseDto.fail(ex.getMessage());
    }

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

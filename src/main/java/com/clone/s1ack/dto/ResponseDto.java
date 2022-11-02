package com.clone.s1ack.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;

    public static <T> ResponseDto<T> success(T data) {

        return new ResponseDto<>(true, data, null);
    }
    public static <T> ResponseDto<T> fail(String message){
        return new ResponseDto<>(false, null, new Error(message));
    }

    @Getter
    @AllArgsConstructor
    static class Error{
        private String message;
    }
}

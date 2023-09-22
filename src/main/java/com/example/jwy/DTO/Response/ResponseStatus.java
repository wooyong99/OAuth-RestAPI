package com.example.jwy.DTO.Response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatus {
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),
    SIGNUP_SUCCESS(true, HttpStatus.OK.value(),"회원가입에 성공하였습니다"),
    SIGNUP_PASSWORD_DIFF(false, HttpStatus.BAD_REQUEST.value(), "비밀번호 확인 값이 다릅니다. 다시 입력해주세요."),
    SIGNUP_PW_INVALID(false, HttpStatus.BAD_REQUEST.value(), "비밀번호 형식이 올바르지 않습니다."),
    SIGNUP_EMAIL_INVALID(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식이 올바르지 않습니다.");

    private final boolean isSuccess;
        private final int code;
        private final String msg;


        private ResponseStatus(boolean isSuccess, int code, String msg){
            this.isSuccess = isSuccess;
            this.code = code;
            this.msg = msg;
        }
}
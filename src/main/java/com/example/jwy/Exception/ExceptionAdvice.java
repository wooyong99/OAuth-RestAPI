package com.example.jwy.Exception;

import com.example.jwy.DTO.Response.ResponseDTO;
import com.example.jwy.DTO.Response.ResponseStatus;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseDTO<ResponseStatus> BaseExceptionHandle(BaseException exception){
        return new ResponseDTO<>(exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO<ResponseStatus> ExceptionHandle(Exception exception){
        return new ResponseDTO<>(ResponseStatus.UNEXPECTED_ERROR);
    }
}

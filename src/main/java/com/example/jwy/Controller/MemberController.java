package com.example.jwy.Controller;

import com.example.jwy.DTO.LoginDTO;
import com.example.jwy.DTO.Response.ResponseDTO;
import com.example.jwy.DTO.Response.ResponseStatus;
import com.example.jwy.DTO.SignupDTO;
import com.example.jwy.Entity.Member;
import com.example.jwy.Service.MemberService;
import com.example.jwy.Util.UserValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/test")
    public String test(@AuthenticationPrincipal String email){

        return String.format("test Page and email : %s",email);
    }
    // 회원가입
    @PostMapping(value="/usr/signup")
    public ResponseDTO signup(@Valid @RequestBody SignupDTO signupDTO, BindingResult bindingResult){
        List<String> error_list = UserValidation.getValidationError(bindingResult);

        if(!error_list.isEmpty()){
            return new ResponseDTO(false, HttpStatus.BAD_REQUEST.value(), error_list);
        }else{
            if(!UserValidation.isRegexEmail(signupDTO.getEmail())){
                return new ResponseDTO(ResponseStatus.SIGNUP_EMAIL_INVALID);
            }
            if(!UserValidation.isRegexPw(signupDTO.getPassword())){
                return new ResponseDTO(ResponseStatus.SIGNUP_PW_INVALID);
            }
            if(!signupDTO.getPassword().equals(signupDTO.getConfirmPw())){
                return new ResponseDTO(ResponseStatus.SIGNUP_PASSWORD_DIFF);
            }
            memberService.signUp(signupDTO.getEmail(), signupDTO.getPassword(), signupDTO.getNickname());
            return new ResponseDTO(ResponseStatus.SIGNUP_SUCCESS);
        }
    }


    @PostMapping(value="/usr/login")
    public ResponseDTO login(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult){
        List<String> error_list = UserValidation.getValidationError(bindingResult);

        if(!error_list.isEmpty()){
            return new ResponseDTO(error_list);
        }
        String token = memberService.login(loginDTO);
        // Security Context에 저장된 사용자 확인
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        return new ResponseDTO(token);
    }
}

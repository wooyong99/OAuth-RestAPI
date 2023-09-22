package com.example.jwy.Controller;

import com.example.jwy.DTO.Response.ResponseDTO;
import com.example.jwy.DTO.Response.ResponseStatus;
import com.example.jwy.DTO.SignupDTO;
import com.example.jwy.Service.MemberService;
import com.example.jwy.Util.UserValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping(value="/usr/signup")
    public ResponseDTO oauthLogin(@Valid @RequestBody SignupDTO signupDTO, BindingResult bindingResult){
        System.out.println(signupDTO.getEmail());
        System.out.println(signupDTO.getPassword());

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
}

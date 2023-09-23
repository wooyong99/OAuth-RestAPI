package com.example.jwy.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message= "email은 필수 입력 사항입니다.")
    private String email;

    @NotBlank(message= "비밀번호는 필수 입력 사항입니다.")
    private String password;
}

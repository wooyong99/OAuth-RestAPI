package com.example.jwy.Service;

import com.example.jwy.DTO.LoginDTO;
import com.example.jwy.Entity.Member;
import com.example.jwy.Exception.BaseException;
import com.example.jwy.Jwt.JwtTokenProvider;
import com.example.jwy.Repository.MemberRepository;
import com.example.jwy.Security.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.jwy.DTO.Response.ResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomAuthenticationProvider customAuthenticationProvider;
    public void signUp(String email, String password, String nickname) {
        if(memberRepository.existsByEmail(email)){
            throw new BaseException(SIGNUP_DUPLI_MEMBER);
        }
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();
        memberRepository.save(member);
    }

    public String login(LoginDTO loginDTO) {
        String token = "";
        try{
            Authentication authentication = customAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContext sc = SecurityContextHolder.createEmptyContext();
            sc.setAuthentication(authentication);
            SecurityContextHolder.setContext(sc);

            token = jwtTokenProvider.createToken(loginDTO.getEmail(),loginDTO.getPassword());
        }catch(UsernameNotFoundException e){
            throw new BaseException(NOT_FOUND_MEMBER);
        } catch(Exception e){
            e.printStackTrace();
            if(e.getMessage().equals("비밀번호가 틀렸습니다.")){
                throw new BaseException(POST_PASSWORD_INCORRECT);
            }
            throw new BaseException(LOGIN_FAIL);
        }

        return token;
    }

    public Member getMemberByEmail(String user_email) {
        return memberRepository.findByEmail(user_email).get();
    }
}

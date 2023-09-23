package com.example.jwy.Security;

import com.example.jwy.Exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.jwy.DTO.Response.ResponseStatus.POST_PASSWORD_INCORRECT;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String email = token.getName();
        String password = (String) token.getCredentials();

        User user = (User) customUserDetailsService.loadUserByUsername(email);

        if(!(user.getUsername().equals(email) && passwordEncoder.matches(password, user.getPassword()))){
            System.out.println("Provider 실패");
            throw new BaseException(POST_PASSWORD_INCORRECT);
        }
        return new UsernamePasswordAuthenticationToken(user, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

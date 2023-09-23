package com.example.jwy.Config;

import com.example.jwy.Entity.Member;
import com.example.jwy.Jwt.JwtExceptionFilter;
import com.example.jwy.Jwt.JwtTokenFilter;
import com.example.jwy.Jwt.JwtTokenProvider;
import com.example.jwy.Repository.MemberRepository;
import com.example.jwy.Service.MemberService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    @Bean
        public WebSecurityCustomizer webSecurityCustomizer(){
            return (web) -> {
                web.ignoring().requestMatchers("/","/usr/**","/auth/kakao/login", "/swagger-ui/**","/v3/api-docs/**");
            };
        }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(c -> c.disable())
                .formLogin( f -> f.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic( h -> h.disable())
                .oauth2Login(
                                        oauth2Config -> oauth2Config
                                                .loginPage("/auth/login")
                                )
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider, memberRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtTokenFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

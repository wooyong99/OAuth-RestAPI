package com.example.jwy.Config;

import com.example.jwy.Service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
public class createMemberData {
    @Bean
    public ApplicationRunner init(MemberService memberService){
        return e -> {
            IntStream.rangeClosed(1,10).forEach( i -> {
                memberService.signUp("member"+i, "1234", "nickname"+i);
            });
        };
    }
}

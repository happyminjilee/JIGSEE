package com.sdi.common.config;

import com.sdi.common.jwt.AuthTokenProvider;
import com.sdi.common.filter.JwtTokenFilter;
import com.sdi.common.dto.MemberPrincipal;
import com.sdi.common.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
// 사용자 정보 확인을 위한 Config
public class AuthenticationConfig {

    private final AuthTokenProvider tokenProvider;
    // Redis를 사용하여 사용자 정보를 캐싱하기 위한 레포지토리
    // private final MemberRefreshTokenRepository MemberRefreshTokenRepository;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 토큰 필터 설정
     */
    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(tokenProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(MemberService memberService) {
        return employeeNo -> memberService
                .loadMemberByEmployeeNo(employeeNo)
                .map(MemberPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with employee no: " + employeeNo));
    }
}

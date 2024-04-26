package com.sdi.member.config;

import com.sdi.member.filter.ExceptionHandlerFilter;
import com.sdi.member.filter.JwtTokenFilter;
import com.sdi.member.jwt.AuthTokenProvider;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthTokenProvider tokenProvider;

    private static final String[] OPEN_ALL = {
            "/v1/login",
            "/v1/refresh"
    };

    private static final String[] OPEN_MANAGER = {
            "/v1/manager/**"
    };

    private static final String[] OPEN_ENGINEER = {
            "/v1/engineer/**"
    };

    private static final String[] OPEN_PRODUCER = {
            "/v1/producer/**"
    };

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_MANAGER > ROLE_ENGINEER " +
                "ROLE_ENGINEER > ROLE_PRODUCER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(OPEN_ALL).permitAll()
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(OPEN_MANAGER).hasAuthority("ROLE_MANAGER")
                        .requestMatchers(OPEN_ENGINEER).hasAuthority("ROLE_ENGINEER")
                        .requestMatchers(OPEN_PRODUCER).hasAuthority("ROLE_PRODUCER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JwtTokenFilter.class)
                .build();
    }
}
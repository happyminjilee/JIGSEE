package com.sdi.common.config;

import com.sdi.common.filter.JwtTokenFilter;
import com.sdi.common.jwt.AuthTokenProvider;
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
            "/api/*/login"
    };

    private static final String[] OPEN_MANAGER = {
            "/api/manager/**"
    };

    private static final String[] OPEN_TECHNIKER = {
            "/api/techniker/**"
    };

    private static final String[] OPEN_PRODUCER = {
            "/api/producer/**"
    };

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_MANAGER > ROLE_TECHNIKER " +
                "ROLE_TECHNIKER > ROLE_PRODUCER";
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
                        .requestMatchers(OPEN_TECHNIKER).hasAuthority("ROLE_TECHNIKER")
                        .requestMatchers(OPEN_PRODUCER).hasAuthority("ROLE_PRODUCER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
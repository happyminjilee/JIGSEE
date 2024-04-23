package com.sdi.common.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public record MemberPrincipal(
        String employeeNo,
        String password,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

    public static MemberPrincipal of(String employeeNo, String password, Collection<? extends GrantedAuthority> authorities) {
        return new MemberPrincipal(employeeNo, password, authorities);
    }

    public static MemberPrincipal from(Member dto) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(dto.role().getCode()));
        return new MemberPrincipal(dto.employeeNo(), dto.password(), authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return employeeNo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

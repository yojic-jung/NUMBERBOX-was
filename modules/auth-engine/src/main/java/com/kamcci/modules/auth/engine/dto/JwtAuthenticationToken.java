package com.kamcci.modules.auth.engine.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Def. jwt 기반 인증 객체
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final transient Object principal;
    private final transient Object credentials;

    public JwtAuthenticationToken(Object principal, Object credentials,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof JwtAuthenticationToken user) {
            return this.getPrincipal().equals(user.getPrincipal()) && this.getCredentials() == user.getCredentials();
        } else {
            return false;
        }
    }
}

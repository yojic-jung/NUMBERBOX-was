package com.numberbox.security.manager;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

/**
 * Def. 인증 Provider에게 인증 절차 위임
 */
public class AuthDelegateManager extends ProviderManager {
    public AuthDelegateManager(AuthenticationProvider authenticationProvider) {
        super(authenticationProvider);
    }
}

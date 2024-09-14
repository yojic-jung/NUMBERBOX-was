package com.kamcci.modules.auth.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.login.url")
public class AuthUrlProperty {
    private final String process; // 로그인 프로세스 url

    public AuthUrlProperty(String process) {
        this.process = process;
    }

    public String getProcess() {
        return process;
    }
}

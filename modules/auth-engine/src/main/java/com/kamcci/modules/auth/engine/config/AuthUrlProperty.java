package com.kamcci.modules.auth.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.login.url")
public record AuthUrlProperty(String process, String fail, String logout) { }


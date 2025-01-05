package com.kamcci.modules.auth.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kamcci.auth.login.url")
public record AuthLoginUrlProperty(String process, String fail, String logout) { }


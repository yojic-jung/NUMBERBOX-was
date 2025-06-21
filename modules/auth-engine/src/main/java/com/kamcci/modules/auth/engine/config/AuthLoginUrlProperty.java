package com.kamcci.modules.auth.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "kamcci.auth.login.url")
public record AuthLoginUrlProperty(String process, String fail, String logout, List<String> except) { }


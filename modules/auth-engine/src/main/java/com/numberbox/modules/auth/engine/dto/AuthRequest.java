package com.numberbox.modules.auth.engine.dto;

/**
 * Def. 로그인 요청 형식(json)
 */
public record AuthRequest(String username, String password) {
}
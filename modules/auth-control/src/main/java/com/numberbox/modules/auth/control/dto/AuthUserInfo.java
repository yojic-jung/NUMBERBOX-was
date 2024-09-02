package com.numberbox.modules.auth.control.dto;

import java.util.List;
import java.util.UUID;

/**
 * Def. 서버(DB)에서 관리하는 사용자 인증 정보
 * - 클라이언트 인증 요청 정보와 서버측 사용자 인증 정보를 비교하기 위해 사용됨
 */
public record AuthUserInfo(String username, UUID userId, String password, List<AuthUserRole> roles) {
}
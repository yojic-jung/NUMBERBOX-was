package com.kamcci.modules.auth.control.dto;

/**
 * Def. 서버(DB)측에서 관리하는 사용자 권한 정보
 * - 클라이언트 인증 요청 정보와 서버측 사용자 인증 정보를 비교하기 위해 사용됨
 */
public record AuthUserRole(String roleName, boolean enabled) {
}

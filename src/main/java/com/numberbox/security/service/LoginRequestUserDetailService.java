package com.numberbox.security.service;

import com.numberbox.security.dto.AuthUserInfo;

/**
 * Def. 서버측 사용자 인증 정보 제공 인터페이스
 * - 모듈 사용자는 해당 인터페이스를 구현하여 서버(DB)에 저장된 사용자 인증정보를 반환해야함
 */
public interface LoginRequestUserDetailService {

    AuthUserInfo loadUserByUsername(String username);
}

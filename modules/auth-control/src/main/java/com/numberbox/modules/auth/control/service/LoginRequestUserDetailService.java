package com.numberbox.modules.auth.control.service;

import com.numberbox.modules.auth.control.dto.AuthUserInfo;

/**
 * Def. 서버측 사용자 인증 정보 제공 인터페이스
 * - 호출 모듈에서 해당 인터페이스를 직접 구현하여 서버(DB)에 저장된 사용자 인증정보를 반환해야함
 */
public interface LoginRequestUserDetailService {

    AuthUserInfo loadUserByUsername(String username);

}

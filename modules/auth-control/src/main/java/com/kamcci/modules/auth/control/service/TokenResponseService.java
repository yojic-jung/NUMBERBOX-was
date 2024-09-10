package com.kamcci.modules.auth.control.service;

import java.util.List;
import java.util.UUID;

/**
 * Def. 토큰을 response에 담음
 */
public interface TokenResponseService {

    // email, userId, roleList를 통해 토큰을 만들어 response에 담음
    void createAndSetTokenToResponse(String email, UUID userId, List<String> roleList);
    
    // accessToken, refreshToken을 response에 담음
    void setTokenToResponse(String accessToken, String refreshToken, List<String> roleList);
}

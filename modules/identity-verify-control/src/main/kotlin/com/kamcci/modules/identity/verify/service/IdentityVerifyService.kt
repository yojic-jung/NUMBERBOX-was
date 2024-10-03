package com.kamcci.modules.identity.verify.service

import com.kamcci.modules.identity.verify.dto.response.IdVerifyResponse
import com.kamcci.modules.identity.verify.vo.CetificationVo
import com.kamcci.modules.identity.verify.vo.IdVerifyMerchantVo

/**
 * 본인인증 서비스
 */
interface IdentityVerifyService {
    // 본인인증
    fun certifyByUserId(uid: String): IdVerifyResponse<CetificationVo>

    // 상점 정보 조회
    fun findMerchantInfo(): IdVerifyMerchantVo
}
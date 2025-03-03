package com.kamcci.numberbox.app.service.mock.port.orm.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeVo
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_EMAIL
import java.time.LocalDateTime

class MockMemberVerifyCodeReadOrmPort : MemberVerifyCodeReadOrmPort {
    companion object {
        const val EXPIRE_CODE_EMAIL = "expire@test.com"
        const val CODE_NOT_EXIST_EMAIL = "codeNotExist@test.com"

        const val VALID_RETURN_CODE = "5c1d1a9a-3e12-488c-be48-88fdb92c2dd0"
        const val MIS_MATCH_CODE = "1c1d1a9a-3e12-488c-be48-88fdb92c2dd0"
    }

    override fun countByEmailAndCodeType(email: String, codeType: VerifyCodeType): Long {
        return if (email == FAIL_EMAIL) 0L else 1L
    }

    override fun readByEmailAndCodeType(email: String, codeType: VerifyCodeType): MemberVerifyCodeVo? {
        return when (email) {
            CODE_NOT_EXIST_EMAIL -> null
            EXPIRE_CODE_EMAIL -> MemberVerifyCodeVo(
                "5c1d1a9a-3e12-488c-be48-88fdb92c2dd0",
                LocalDateTime.now().minusMinutes(20)
            )

            else -> MemberVerifyCodeVo(VALID_RETURN_CODE, LocalDateTime.now())
        }
    }
}
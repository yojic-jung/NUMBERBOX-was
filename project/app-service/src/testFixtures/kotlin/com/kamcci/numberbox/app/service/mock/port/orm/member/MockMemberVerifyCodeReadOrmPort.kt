package com.kamcci.numberbox.app.service.mock.port.orm.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeVo
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeReadOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_EMAIL
import java.time.LocalDateTime

class MockMemberVerifyCodeReadOrmPort : MemberVerifyCodeReadOrmPort {
    companion object {
        // 만료된 코드를 가진 회원 이메일
        const val EXPIRE_CODE_EMAIL = "expire@test.com"

        // 코드를 갖고 있지 않은 회원 이메일
        const val CODE_NOT_EXIST_EMAIL = "codeNotExist@test.com"

        // 정상 케이스 시 코드 반환값
        const val VALID_RETURN_CODE = "5c1d1a9a-3e12-488c-be48-88fdb92c2dd0"

        // 만료된 코드
        const val EXPIRE_VALID_CODE = "7a4e1a9a-3e12-488c-be48-88fdb92c2dd0"

        // 아래 코드로 테스트 불일치 실패 발생
        const val MIS_MATCH_CODE = "1c1d1a9a-3e12-488c-be48-88fdb92c2dd0"
    }

    override fun countByEmailAndCodeType(email: String, codeType: VerifyCodeType): Long {
        return if (email == FAIL_EMAIL) 0L else 1L
    }

    override fun readByEmailAndCodeType(email: String, codeType: VerifyCodeType): MemberVerifyCodeVo? {
        return when (email) {
            CODE_NOT_EXIST_EMAIL -> null
            EXPIRE_CODE_EMAIL -> MemberVerifyCodeVo(EXPIRE_VALID_CODE, LocalDateTime.now().minusMinutes(20))
            else -> MemberVerifyCodeVo(VALID_RETURN_CODE, LocalDateTime.now())
        }
    }
}
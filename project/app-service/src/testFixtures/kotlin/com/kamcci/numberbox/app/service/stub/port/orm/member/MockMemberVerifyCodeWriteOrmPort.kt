package com.kamcci.numberbox.app.service.stub.port.orm.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_EMAIL

class MockMemberVerifyCodeWriteOrmPort : MemberVerifyCodeWriteOrmPort {
    override fun save(memberVerifyCodeSaveDto: MemberVerifyCodeSaveDto): Boolean {
        return memberVerifyCodeSaveDto.email != FAIL_EMAIL
    }
}
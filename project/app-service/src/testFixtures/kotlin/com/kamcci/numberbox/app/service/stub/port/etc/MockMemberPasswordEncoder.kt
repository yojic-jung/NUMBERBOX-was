package com.kamcci.numberbox.app.service.stub.port.etc

import com.kamcci.numberbox.app.port.etc.MemberPasswordEncoder
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_STRING

class MockMemberPasswordEncoder : MemberPasswordEncoder {
    override fun encode(rawPassword: CharSequence): String {
        return "encoded"
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return rawPassword != FAIL_STRING
    }
}
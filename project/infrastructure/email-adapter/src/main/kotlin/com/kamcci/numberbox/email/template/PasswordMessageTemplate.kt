package com.kamcci.numberbox.email.template

import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import org.springframework.stereotype.Component

@Component("password")
class PasswordMessageTemplate : EmailMessageTemplate {
    override val title: String
        get() = "[N명의수학] 임시 비밀번호 안내"

    override fun getContent(code: String) = "<div>" +
            "안녕하세요. N명의수학입니다.<br/>" +
            "요청하신 회원님의 임시 비밀번호는 다음과 같습니다." +
            "</div>" +
            "<div style='margin:\"10px 0\"font-family:\"Malgun Gothic\";font-size:\"20px\"; '>" +
            code +
            "</div>" +
            "임시 비밀번호는 오전 06시까지 유효하니 로그인 후 임시 비밀번호를 변경하여 주시기 바랍니다."
}
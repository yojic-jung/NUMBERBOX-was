package com.kamcci.numberbox.email.template

import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import com.kamcci.numberbox.app.domain.system.construction.Aliases
import org.springframework.stereotype.Component

@Aliases("emailVerify")
@Component
class EmailVerifyMessageTemplate : EmailMessageTemplate {
    override val title: String
        get() = "[N명의수학] 이메일 인증코드 안내"

    override fun getContent(code: String) =
        "<div>" +
                "안녕하세요. N명의수학입니다.<br/>" +
                "요청하신 회원가입 이메일 인증코드는 아래와 같습니다." +
                "</div>" +
                "<div style='margin:\"10px 0\"font-family:\"Malgun Gothic\";font-size:\"20px\"; '>" +
                code +
                "</div>" +
                "위 인증코드는 3분간 유효합니다."

}
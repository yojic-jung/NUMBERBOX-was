package com.kamcci.numberbox.restapi.util.auth

import com.kamcci.modules.auth.control.util.AuthPasswordEncoder
import com.kamcci.numberbox.app.port.etc.MemberPasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthPasswordEncoderWrapper(
    val authPasswordEncoder: AuthPasswordEncoder
) : MemberPasswordEncoder {

    override fun matches(rawPassword: CharSequence, encodedPassword: String) =
        authPasswordEncoder.matches(rawPassword, encodedPassword)

    override fun encode(rawPassword: CharSequence) =
        authPasswordEncoder.encode(rawPassword)
}
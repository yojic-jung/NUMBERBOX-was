package com.kamcci.numberbox.app.repository.member

/**
 * Def. 비밀번호 암호화 인코더
 */
interface MemberPasswordEncoder {
    /**
     * rawPassword를 암호화한 결과가  encodedPassword인지 비교함
     */
    fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean

    /**
     * 문자열 비밀번호를 암호화함
     */
    fun encode(rawPassword: CharSequence): String
}
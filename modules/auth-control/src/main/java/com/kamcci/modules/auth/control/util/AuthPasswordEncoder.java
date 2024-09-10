package com.kamcci.modules.auth.control.util;

/**
 * Def. 비밀번호 암호화 인코더
 */
public interface AuthPasswordEncoder {
    /**
     * rawPassword를 암호화한 결과가  encodedPassword인지 비교함
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);

    /**
     * 문자열 비밀번호를 암호화함
     */
    String encode(CharSequence rawPassword);
}

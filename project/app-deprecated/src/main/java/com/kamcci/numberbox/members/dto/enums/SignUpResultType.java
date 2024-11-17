package com.kamcci.numberbox.members.dto.enums;

public enum SignUpResultType {
    SUCCESS( "성공"),
    EMAIL_CODE_EXPIRED( "만료된 이메일 코드입니다."),
    EMAIL_CODE_MISS_MATCH("이메일 코드가 일치하지 않습니다."),
    EMAIL_EXIST("이미 존재하는 이메일입니다."),
    DROP_ACCOUNT("탈퇴한 계정입니다.");

    public String message;
    SignUpResultType(String message) {
        this.message = message;
    }
}
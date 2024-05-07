package com.numberbox.security.exception;

public class BadAuthRequestException extends RuntimeException{
    public BadAuthRequestException(){
        super("잘못된 형식의 인증 요청입니다.");
    }
    public BadAuthRequestException(String msg){
        super(msg);
    }
}

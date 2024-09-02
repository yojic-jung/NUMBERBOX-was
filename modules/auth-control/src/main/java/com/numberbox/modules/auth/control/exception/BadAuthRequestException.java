package com.numberbox.modules.auth.control.exception;

/**
 * 클라이언트의 잘못된 형식 인증 요청
 */
public class BadAuthRequestException extends RuntimeException{
    public BadAuthRequestException(){
        super("잘못된 형식의 인증 요청입니다.");
    }
    public BadAuthRequestException(String msg){
        super(msg);
    }
}

package com.kamcci.modules.mail.sender.service

/**
 * 메일 전송
 */
interface MailSendService {

    /**
     * 메일 전송(contentType:text/plain)
     * recipientEmail : 수신인 이메일
     * title : 메일 제목
     * contents : 메일 본문
     */
    fun sendTextMessage(recipientEmail: String, title: String, contents: String)

    // 메일 전송(contentType: text/html)
    fun sendHTMLMessage(recipientEmail: String, title: String, contents: String)
}
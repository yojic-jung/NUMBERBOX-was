package com.kamcci.modules.mail.sender.service

/**
 * 메일 전송
 */
interface MailSendService {

    /**
     * 메일 전송(contentType: text/html)
     *
     * @param recipientEmail : 수신인 이메일
     * @param title : 메일 제목
     * @param contents : 메일 본문
     */
    fun sendHTMLMessage(recipientEmail: String, title: String, contents: String)
}
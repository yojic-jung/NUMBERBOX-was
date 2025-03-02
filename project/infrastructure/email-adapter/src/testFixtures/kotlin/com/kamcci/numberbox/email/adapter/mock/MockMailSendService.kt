package com.kamcci.numberbox.email.adapter.mock

import com.kamcci.modules.mail.sender.service.MailSendService

class MockMailSendService : MailSendService {
    override fun sendHTMLMessage(recipientEmail: String, title: String, contents: String) {

    }
}
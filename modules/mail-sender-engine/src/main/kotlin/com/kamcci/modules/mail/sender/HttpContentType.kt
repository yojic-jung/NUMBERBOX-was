package com.kamcci.modules.mail.sender

/**
 * http content-type
 */
enum class HttpContentType(val type: String) {
    TEXT("text/plain; charset=utf-8"),
    HTML("text/html; charset=utf-8")
}
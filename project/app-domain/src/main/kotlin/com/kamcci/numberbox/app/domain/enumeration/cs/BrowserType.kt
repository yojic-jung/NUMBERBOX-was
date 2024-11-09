package com.kamcci.numberbox.app.domain.enumeration.cs

/**
 * 브라우저 정보
 */
enum class BrowserType(val id: String, val desc: String) {
    Chrome("chrome", "크롬"),
    Safari("safari", "사파리"),
    Firefox("firefox", "파이어폭스"),
    Edg("edg", "엣지"),
    Opr("opr", "오페라"),
    Etc("etc", "기타"),
}
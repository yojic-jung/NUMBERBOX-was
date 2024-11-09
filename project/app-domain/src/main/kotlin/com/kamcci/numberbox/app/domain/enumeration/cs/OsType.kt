package com.kamcci.numberbox.app.domain.enumeration.cs

/**
 * OS 정보
 */
enum class OsType(val id: String, val desc: String) {
    Windows("windows", "윈도우즈"),
    Mac("mac", "맥"),
    Etc("etc", "기타"),
}
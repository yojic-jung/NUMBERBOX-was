package com.kamcci.numberbox.app.domain.enumeration.math

/**
 * 수학 문제 타입
 */
enum class ContentsClassifyType(val dbData: Int, val title: String) {
    InHouse(0, "자체 제작"),
    UserCustom(1, "사용자 제작"),
    Modified(2, "변형문제"),
    Deleted(3, "변형문제 존재하는데 삭제한 경우 또는 탈퇴 회원 문제"),
    Ipsi(4, "수능 모의고사 문제"),
}
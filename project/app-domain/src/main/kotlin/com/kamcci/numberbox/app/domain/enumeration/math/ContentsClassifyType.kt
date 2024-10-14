package com.kamcci.numberbox.app.domain.enumeration.math

/**
 * 수학 문제 타입
 */
enum class ContentsClassifyType(val id: Int, val title: String) {
    InHouse(0, "자체 제작"),
    UserCustom(1, "사용자 제작"),
    Modified(1, "변형문제"),
    Deleted(3, "삭제된 문제(또는 탈퇴 회원 문제)"),
    Ipsi(4, "수능 모의고사 문제"),
}
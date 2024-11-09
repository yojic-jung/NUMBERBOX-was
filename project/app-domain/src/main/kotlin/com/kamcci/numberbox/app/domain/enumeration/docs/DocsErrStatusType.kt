package com.kamcci.numberbox.app.domain.enumeration.docs

enum class DocsErrStatusType(val id: Int, val desc: String) {
    None(0, "에러 없음"),
    Self(1, "사용자 직접 신고"),
    Error(2, "에러 발생하여 사용자가 신고한 경우, 오류 신고한 학습지 삭제, 관리자 오류 해결 후 삭제 처리"),

    // 나의 제작문제로 학습지 생성한 경우 DB에 저장, but 사용자 학습지 내역에 보이지는 않음
    MyContents(3, "나의 제작문제로 학습지 생성한 경우"),
}
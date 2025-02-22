package com.kamcci.numberbox.app.domain.enumeration.docs

enum class DocsStatusType(val dbData: Int, val desc: String) {
    None(0, "에러 없음"),
    Self(1, "사용자 직접 신고"),

    // (사용안함) 관리자는 삭제한 컨텐츠도 볼수 있도록 처리
    Error(2, "사용자가 직접 신고한 학습지 삭제한 경우"),

    // 나의 제작문제로 학습지 생성한 경우 DB에 저장, but 사용자 학습지 내역에 보이지는 않음
    MyContents(3, "나의 제작문제로 학습지 생성한 경우"),
}
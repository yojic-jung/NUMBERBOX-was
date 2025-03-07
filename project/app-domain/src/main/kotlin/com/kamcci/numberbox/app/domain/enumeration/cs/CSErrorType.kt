package com.kamcci.numberbox.app.domain.enumeration.cs

/**
 * 고객센터 - 문의 타입
 */
enum class CSErrorType(val dbData: Int, val desc: String) {
    Etc(0, "기타"),
    MathContents(1, "수학 문제"),
    MathResource(2, "수학 컨텐츠(도형 이미지)"),
    MathDocs(3, "학습지"),
    MathEditor(4, "문제 만들기"),
    HwpConvert(5, "hwp to web 파일변환기"),
}
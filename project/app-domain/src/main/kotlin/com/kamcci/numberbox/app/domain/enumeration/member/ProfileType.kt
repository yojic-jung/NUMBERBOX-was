package com.kamcci.numberbox.app.domain.enumeration.member

/**
 * 회원 프로필 타입
 */
enum class ProfileType(val dbData: Int, val desc: String) {
    None(0, "미등록"),
    HeadOfAcademy(1, "원장"),
    Instructor(2, "강사"),
    Teacher(3, "교사"),
    SchoolParent(4, "학부모"),
    Student(5, "학생"),
    Etc(6, "기타"),
}
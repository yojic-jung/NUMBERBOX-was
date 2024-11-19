package com.kamcci.numberbox.app.domain.enumeration.port.storage

/**
 * 파일 구분 타입
 *
 * - actionId + path 의 길이가 22글자 넘으면 안됨(db 제약조건)
 */
enum class FileType(val title: String, val path: String, val newName: String) {
    CsErrIMG("cs 문의사항 이미지", "csError", "CS"),
    ProfileIMG("프로필 이미지", "profileImg", "PROFILE"),
    EditorUploadIMG("에디터 업로드 이미지", "editorImgUpld", "MATH"),
    HwpToHTML("hwp to html 변환 파일", "hwpToHtml", "DOCS"),
    PptResource("학습자료 ppt", "resource/ppt", "CONTENTS"),
    PptImage("학습자료 img", "resource/img", "CONTENTS")
}
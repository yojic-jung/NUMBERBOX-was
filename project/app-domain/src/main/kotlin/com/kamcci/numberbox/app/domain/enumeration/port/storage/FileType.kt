package com.kamcci.numberbox.app.domain.enumeration.port.storage

/**
 * 파일 구분 타입
 *
 * - actionId + path 의 길이가 22글자 넘으면 안됨(db 제약조건)
 */
enum class FileType(val actionId: Int, val title: String, val path: String, val newName: String) {
    CsErrIMG(8, "cs 문의사항 이미지", "csError", "cs"),
    ProfileIMG(9, "프로필 이미지", "profileImg", "profile"),
    EditorUploadIMG(10, "에디터 업로드 이미지", "editorImgUpld", "math"),
    HwpToHTML(11, "hwp to html 변환 파일", "hwpToHtml", "docs"),
    PptResource(12, "학습자료 ppt", "resource/ppt", "contents"),
    PptImage(13, "학습자료 img", "resource/img", "contents")
}
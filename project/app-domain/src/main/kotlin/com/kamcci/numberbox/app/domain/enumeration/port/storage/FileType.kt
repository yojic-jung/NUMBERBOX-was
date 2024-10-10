package com.kamcci.numberbox.app.domain.enumeration.port.storage

/**
 * 파일 구분 타입
 */
enum class FileType(val actionId: Int, val title: String, val path: String) {
    ProfileIMG(9, "프로필 이미지", "profileImg"),
    EditorUploadIMG(10, "에디터 업로드 이미지", "editorImgUpld"),
    HwpToHTML(11, "hwp to html 변환 파일", "hwpToHtml"),
}
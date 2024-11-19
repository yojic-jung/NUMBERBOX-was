package com.kamcci.numberbox.app.domain.dto.resource

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto

/**
 * 수학 학습 자료 수정용
 */
data class MathResourceUpdateDto(
    val resourceId: Long,
    // 제목
    val title: String,
    // 대표 카테고리
    val mainCateId: String,
    // 대표 세부 카테고리
    val midCateId: String,
    // 카테고리 리스트('mainCateId-midCateId' 문자열 형식)
    val cateList: List<String>,
    // 학습자료 ppt
    val pptFile: FileUploadDto?,
    // ppt 슬라이드 이미지
    val slideImgList: List<FileUploadDto>,
    // 대표이미지
    val imgFile: FileUploadDto?,
)
package com.kamcci.numberbox.restapi.dto.request.resource

import org.springframework.web.multipart.MultipartFile

/**
 * 수학 학습 자료 등록용
 */
data class MathResourceCreateRequest(
    // 제목
    val title: String,
    // 대표 카테고리
    val mainCateId: String,
    // 대표 세부 카테고리
    val midCateId: String,
    // 카테고리 리스트('mainCateId-midCateId' 문자열 형식)
    val cateList: List<String>,
    // 학습자료 ppt
    val pptFile: MultipartFile,
    // 대표이미지
    val imgFile: MultipartFile?,
)

package com.kamcci.numberbox.restapi.dto.request.resource

import com.kamcci.numberbox.restapi.validation.file.ImgFileCheck
import com.kamcci.numberbox.restapi.validation.file.PptFileCheck
import org.springframework.web.multipart.MultipartFile

/**
 * 수학 학습 자료 수정용 요청
 */
data class MathResourceUpdateRequest(
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
    @field:PptFileCheck
    val pptFile: MultipartFile?,
    // 대표이미지
    @field:ImgFileCheck
    val imgFile: MultipartFile?,
)

package com.kamcci.numberbox.app.domain.dto.resource

import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo

/**
 * MathResourceEntity 수정 영속화 목적 dto
 */
data class MathResourceUpdtOrmDto(
    val resourceId: Long,
    // 학습 자료 제목
    val title: String,
    // ppt 파일
    val pptFilePath: String?,
    val pptFileName: String?,
    val pptPageCnt: Int?,
    // 대표 이미지 경로
    val imgPath: String?,
    // 대표 이미지명
    val imgName: String?,
    // 카테고리 리스트('mainCateId-midCateId' 문자열 형식)
    val cateList: List<String>,
    // 슬라이드
    val imgList: MutableList<FileNameVo>
)
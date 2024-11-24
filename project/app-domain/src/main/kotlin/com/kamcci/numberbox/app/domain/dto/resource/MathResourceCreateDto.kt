package com.kamcci.numberbox.app.domain.dto.resource

import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import java.util.*

/**
 * 수학 학습 자료 등록용
 */
data class MathResourceCreateDto(
    // 등록자 memberId
    val memberId: UUID,
    // 학습 자료 제목
    val title: String,
    // ppt 파일
    val pptFilePath: String,
    val pptFileName: String,
    val pptPageCnt: Int,
    // 대표 이미지 경로
    val imgPath: String,
    // 대표 이미지명
    val imgName: String,
    // 카테고리
    val cateList: List<String>,
    // 슬라이드
    val imgList: List<FileNameVo>
)
package com.kamcci.numberbox.app.port.orm.resource

import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo

/**
 * 학습 자료 슬라이드 이미지 영속화
 */
interface MathResourceImgModifyOrmPort {

    // 영속화
    fun create(resourceId: Long, imgList: List<FileNameVo>)

    // 삭제
    fun deleteByResourceId(resourceId: Long): Long
}
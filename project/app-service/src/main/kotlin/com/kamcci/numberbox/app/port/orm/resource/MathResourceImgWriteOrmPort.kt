package com.kamcci.numberbox.app.port.orm.resource

/**
 * 학습 자료 슬라이드 이미지 영속화
 */
interface MathResourceImgWriteOrmPort {

    // 삭제
    fun deleteByResourceId(resourceId: Long): Long
}
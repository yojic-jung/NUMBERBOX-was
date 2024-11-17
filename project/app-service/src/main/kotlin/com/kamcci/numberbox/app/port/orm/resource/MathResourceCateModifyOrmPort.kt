package com.kamcci.numberbox.app.port.orm.resource

/**
 * 학습 자료 카테고리 영속화
 */
interface MathResourceCateModifyOrmPort {
    // 영속화
    fun create(resourceId: Long, cateList: List<String>)

    // 삭제
    fun deleteByResourceId(resourceId: Long): Long
}
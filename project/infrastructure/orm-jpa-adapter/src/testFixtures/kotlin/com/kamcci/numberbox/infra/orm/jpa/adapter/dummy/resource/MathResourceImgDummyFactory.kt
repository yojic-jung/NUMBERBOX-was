package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.resource

/**
 * MathResourceImgEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object MathResourceImgDummyFactory {
    fun getMathResourceImgDummyEntity() = ExistEntityInfo(1, 1)

    class ExistEntityInfo(val id: Long, val resourceId: Long)

}
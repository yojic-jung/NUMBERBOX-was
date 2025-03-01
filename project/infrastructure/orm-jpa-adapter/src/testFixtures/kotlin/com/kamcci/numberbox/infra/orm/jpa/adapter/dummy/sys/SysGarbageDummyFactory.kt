package com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.sys

/**
 * SysGarbageFileEntity 더미 데이터
 * - flyway를 통해 반영한 테스트 목적 데이터 정보
 */
object SysGarbageDummyFactory {
    fun getSysGarbageFileDummyEntity() = ExistEntityInfo(1)

    class ExistEntityInfo(val id: Long)

}
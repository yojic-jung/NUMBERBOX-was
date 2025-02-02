package com.kamcci.numberbox.app.service.stub.usecase.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileReadCase

class MockSysGarbageFileReadCase : SysGarbageFileReadCase {
    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var moreBatchSize = false // 배치 사이즈 이상 조회 여부
    var executeCnt = 0 // 실행 횟수
    override fun readAllByType(type: GarbageFileType, limit: Long): List<SysGarbageFileVo> {
        executeCnt++
        val garbageList: MutableList<SysGarbageFileVo> = mutableListOf()
        if (moreBatchSize && executeCnt == 1) {
            for (i in 0..600) garbageList.add(SysGarbageFileVo(1L, GarbageFileType.S3, "", "", 0))
        } else {
            for (i in 0..100) garbageList.add(SysGarbageFileVo(1L, GarbageFileType.S3, "", "", 0))
        }
        return garbageList
    }
}
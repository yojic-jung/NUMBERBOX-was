package com.kamcci.numberbox.app.service.stub.usecase.sys

import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileWriteCase

class MockSysGarbageFileWriteCase : SysGarbageFileWriteCase {
    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var excutedCnt = 0

    override fun deleteById(idList: List<Long>): Long {
        if (idList.isNotEmpty()) excutedCnt++
        return idList.size.toLong()
    }

    override fun incrementFailCntById(id: List<Long>): Long {
        return id.size.toLong()
    }
}
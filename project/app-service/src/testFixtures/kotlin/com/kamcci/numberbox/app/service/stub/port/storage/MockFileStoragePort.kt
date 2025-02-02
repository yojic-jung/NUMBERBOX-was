package com.kamcci.numberbox.app.service.stub.port.storage

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_STRING
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG

class MockFileStoragePort : FileStoragePort {
    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var isThrowException = false // 예외 발생 여부

    override fun upload(uploadDto: FileUploadDto) {
        if (uploadDto.name == FAIL_STRING) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun delete(fileName: String) {
        if (fileName == FAIL_STRING || isThrowException) throw RuntimeException(STUB_EXCEPTION_MSG)
    }
}
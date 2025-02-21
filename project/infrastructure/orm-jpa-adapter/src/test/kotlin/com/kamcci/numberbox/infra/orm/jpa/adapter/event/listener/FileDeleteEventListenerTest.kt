package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.service.mock.port.orm.sys.MockSysGarbageFileWriteOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class FileDeleteEventListenerTest {
    @Test
    fun `파일 삭제 이벤트 리스너 검증`() {
        val sysGarbageFileWriteOrmPort = MockSysGarbageFileWriteOrmPort()
        val fileDeleteEventListener = FileDeleteEventListener(sysGarbageFileWriteOrmPort)
        // given
        val deleteDto = FileDeleteDto(GarbageFileType.S3, "", "")

        // when
        assertDoesNotThrow {
            fileDeleteEventListener.save(deleteDto)
        }
    }

}
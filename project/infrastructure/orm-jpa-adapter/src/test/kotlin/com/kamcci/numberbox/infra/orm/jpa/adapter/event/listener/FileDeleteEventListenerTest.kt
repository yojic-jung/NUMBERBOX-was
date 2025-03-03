package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.service.mock.port.orm.sys.MockSysGarbageFileWriteOrmPort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class FileDeleteEventListenerTest {
    private val sysGarbageFileWriteOrmPort = MockSysGarbageFileWriteOrmPort()
    private val fileDeleteEventListener = FileDeleteEventListener(sysGarbageFileWriteOrmPort)

    @Test
    fun `파일 삭제 이벤트 리스너 검증`() {

        // given
        val deleteDto = FileDeleteDto(GarbageFileType.S3, "any", "any")

        // when
        assertDoesNotThrow {
            fileDeleteEventListener.save(deleteDto)
        }
    }

}
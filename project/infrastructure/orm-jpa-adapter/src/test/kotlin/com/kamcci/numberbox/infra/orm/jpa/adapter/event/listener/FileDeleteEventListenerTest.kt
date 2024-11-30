package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBSpringTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.ApplicationEventPublisher

@TcDBSpringTest
class FileDeleteEventListenerTest(
    @Autowired
    private val eventPublisher: ApplicationEventPublisher,
    @SpyBean
    private val sysGarbageFileWriteOrmPort: SysGarbageFileWriteOrmPort
) {
    @Test
    fun `파일 삭제 이벤트 발행 검증`() {
        // given
        val deleteDto = FileDeleteDto(GarbageFileType.S3, "", "")

        // when
        eventPublisher.publishEvent(deleteDto)

        // then
        Mockito.verify(sysGarbageFileWriteOrmPort).create(deleteDto)
    }
}
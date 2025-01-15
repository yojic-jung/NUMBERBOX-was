package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBSpringMockConfigTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.sys.SysGarbageFileWriteRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.transaction.annotation.Transactional

@TcDBSpringMockConfigTest
class FileDeleteEventListenerTest @Autowired constructor(
    private val eventPublisher: ApplicationEventPublisher,
    private val sysGarbageFileWriteRepository: SysGarbageFileWriteRepository
) {
    @Transactional
    @Test
    fun `파일 삭제 이벤트 리스너 검증`() {
        // given
        val deleteDto = FileDeleteDto(GarbageFileType.S3, "", "")

        // when
        eventPublisher.publishEvent(deleteDto)

        // then
        Mockito.verify(sysGarbageFileWriteRepository).create(deleteDto)
    }

}
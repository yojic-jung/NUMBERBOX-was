package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlResponseEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpResponseEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp.HwpConvertFileWriteRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class HwpConvertCompleteEventListener(
    private val hwpConvertFileWriteRepository: HwpConvertFileWriteRepository,
) {
    @EventListener
    @Transactional
    fun processConvertResponseEvent(event: JsonToHwpResponseEvent) {
        hwpConvertFileWriteRepository.update(event.id, event.fileName)
    }

    @EventListener
    @Transactional
    fun processConvertResponseEvent(event: HwpToHtmlResponseEvent) {
        hwpConvertFileWriteRepository.update(event.id, event.fileName)
    }
}

package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertRequestResultEvent
import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestResultEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.hwp.HwpConvertFileWriteRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class HwpConvertEventListener(
    private val hwpConvertFileWriteRepository: HwpConvertFileWriteRepository,
) {
    @Async
    @EventListener
    @Transactional
    fun processConvertRequestEvent(event: HwpConvertRequestResultEvent) {
        hwpConvertFileWriteRepository.updateIsRequestSuccess(event.id, event.isSuccess)
    }

    @Async
    @EventListener
    @Transactional
    fun processConvertRequestEvent(event: HwpToHtmlRequestResultEvent) {
        hwpConvertFileWriteRepository.updateIsRequestSuccess(event.id, event.isSuccess)
    }
}
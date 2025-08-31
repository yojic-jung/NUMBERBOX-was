package com.kamcci.numberbox.restapi.scheduler.convert

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpConvertFileType
import com.kamcci.numberbox.app.port.hwp.HwpConvertEventPort
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertFileReadCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertFileWriteCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class HwpConvertScheduler(
    private val hwpConvertFileReadCase: HwpConvertFileReadCase,
    private val hwpConvertEventPort: HwpConvertEventPort,
    private val hwpConvertFileWriteCase: HwpConvertFileWriteCase,
) {
    // 1시간 마다 실행
    @Scheduled(cron = "00 00 * * * *")
    fun retryRequestFailMessage() {
        hwpConvertFileReadCase.readByRequestAtLoe(LocalDateTime.now().minusMinutes(60))
            .forEach { failMessage ->
                // 메시지 재 발행
                if (failMessage.convertType == HwpConvertFileType.JsonToHwp) {
                    val jsonToHwpReq = JsonToHwpRequestEvent(failMessage.id, failMessage.fileName)
                    hwpConvertEventPort.requestHwp(jsonToHwpReq)
                } else {
                    val hwpToHtmlReq = HwpToHtmlRequestEvent(failMessage.id, failMessage.fileName)
                    hwpConvertEventPort.requestHtml(hwpToHtmlReq)
                }

                // 처리 성공으로 변환
                hwpConvertFileWriteCase.updateIsRequestSuccess(failMessage.id, true)
            }
    }
}
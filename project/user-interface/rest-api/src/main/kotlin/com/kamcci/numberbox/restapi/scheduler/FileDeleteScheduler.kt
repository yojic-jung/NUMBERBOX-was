package com.kamcci.numberbox.restapi.scheduler

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileModifyUseCase
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileReadUseCase
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FileDeleteScheduler(
    private val sysGarbageFileReadUseCase: SysGarbageFileReadUseCase,
    private val sysGarbageFileModifyUseCase: SysGarbageFileModifyUseCase
) {
    private val log = LoggerFactory.getLogger(javaClass)

    // 삭제 대상 파일 제거(매일 06시) todo test
    @Scheduled(cron = "00 00 06 * * *")
    fun deleteS3GarbageFile() {
        // 삭제 대상 s3 이미지 조회
        val deleteTargetFile = sysGarbageFileReadUseCase.readAllByType(GarbageFileType.S3)
        // 유휴 파일 삭제
        deleteTargetFile.forEach {
            val isDeleted = sysGarbageFileModifyUseCase.delete(it)
            if (!isDeleted) log.info("[파일 삭제 실패] id : ${it.id}")
        }
    }
}
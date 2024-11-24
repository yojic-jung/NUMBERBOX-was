package com.kamcci.numberbox.restapi.scheduler.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileReadCase
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileWriteCase
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FileDeleteScheduler(
    private val fileStoragePort: FileStoragePort,
    private val sysGarbageFileReadCase: SysGarbageFileReadCase,
    private val sysGarbageFileWriteCase: SysGarbageFileWriteCase
) {
    companion object {
        const val BATCH_SIZE = 500L
    }

    private val log = LoggerFactory.getLogger(javaClass)

    // 삭제 대상 파일 제거
    @Scheduled(cron = "00 00 03 * * *")
    fun deleteS3GarbageFile() {
        log.info("유휴 파일 삭제 배치 시작")
        while (true) {
            // 삭제 대상 s3 이미지 조회
            val deleteTargetFile = sysGarbageFileReadCase.readAllByType(GarbageFileType.S3, BATCH_SIZE)

            val successIdList: MutableList<Long> = mutableListOf()
            val failIdList: MutableList<Long> = mutableListOf()
            deleteTargetFile.forEach {
                try {
                    // 유휴 파일 삭제
                    fileStoragePort.delete("${it.path}/${it.name}")
                    successIdList.add(it.id)
                } catch (e: Exception) {
                    log.warn("유휴 파일 삭제 실패 : ${e.message}")
                    failIdList.add(it.id)
                }
            }

            // 성공건 db에서 삭제
            sysGarbageFileWriteCase.deleteById(successIdList)
            // 실패건 failCnt+1
            sysGarbageFileWriteCase.incrementFailCntById(failIdList)

            // 더이상 조회할 데이터 없다면 종료
            if (deleteTargetFile.size < BATCH_SIZE) break
        }
        log.info("유휴 파일 삭제 배치 종료")
    }
}
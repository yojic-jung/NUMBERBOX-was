package com.kamcci.numberbox.restapi.scheduler.sys

import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.service.mock.port.storage.MockFileStoragePort
import com.kamcci.numberbox.app.service.mock.usecase.sys.MockSysGarbageFileReadCase
import com.kamcci.numberbox.app.service.mock.usecase.sys.MockSysGarbageFileWriteCase
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileReadCase
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileWriteCase
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.scheduling.annotation.Scheduled
import java.lang.reflect.Method

class FileDeleteSchedulerTest {
    private val fileStoragePort: FileStoragePort = MockFileStoragePort()
    private val sysGarbageFileReadCase: SysGarbageFileReadCase = MockSysGarbageFileReadCase()
    private val sysGarbageFileWriteCase: SysGarbageFileWriteCase = MockSysGarbageFileWriteCase()
    private val fileDeleteScheduler =
        FileDeleteScheduler(fileStoragePort, sysGarbageFileReadCase, sysGarbageFileWriteCase)

    @Test
    fun `삭제 대상 파일 제거 3시 설정 - 성공`() {
        // when
        val method: Method = fileDeleteScheduler::class.java.getMethod("deleteS3GarbageFile")
        val scheduledAnnotation = method.getAnnotation(Scheduled::class.java)
        val cronExpression = scheduledAnnotation.cron

        // then
        assertThat(cronExpression).isEqualTo("00 00 03 * * *")
    }

    @Test
    fun `삭제 대상 파일 제거 - 성공(배치 사이즈 보다 작은 경우)`() {
        // given
        val mockSysGarbageFileReadCase = MockSysGarbageFileReadCase()
        val fileDeleteScheduler =
            FileDeleteScheduler(fileStoragePort, mockSysGarbageFileReadCase, sysGarbageFileWriteCase)
        // 배치사이즈보다 작게 조회되도록 설정
        mockSysGarbageFileReadCase.moreBatchSize = false

        // when
        fileDeleteScheduler.deleteS3GarbageFile()

        assertThat(mockSysGarbageFileReadCase.executeCnt).isOne()
    }

    @Test
    fun `삭제 대상 파일 제거 - 성공(배치 사이즈 보다 큰 경우)`() {
        // given
        val mockSysGarbageFileReadCase = MockSysGarbageFileReadCase()
        val fileDeleteScheduler =
            FileDeleteScheduler(fileStoragePort, mockSysGarbageFileReadCase, sysGarbageFileWriteCase)
        // 배치사이즈보다 작게 조회되도록 설정
        mockSysGarbageFileReadCase.moreBatchSize = true

        // when
        fileDeleteScheduler.deleteS3GarbageFile()

        assertThat(mockSysGarbageFileReadCase.executeCnt).isEqualTo(2)
    }

    @Test
    fun `삭제 대상 파일 제거 - 실패`() {
        // given
        val mockFileStoragePort = MockFileStoragePort()
        val mockSysGarbageFileWriteCase = MockSysGarbageFileWriteCase()
        val fileDeleteScheduler =
            FileDeleteScheduler(mockFileStoragePort, sysGarbageFileReadCase, mockSysGarbageFileWriteCase)
        // 배치사이즈보다 작게 조회되도록 설정
        mockFileStoragePort.isThrowException = true

        // when
        fileDeleteScheduler.deleteS3GarbageFile()

        // then -> 성공 케이스 없으므로 성공 후처리 실행 안됨
        assertThat(mockSysGarbageFileWriteCase.excutedCnt).isZero()
    }
}
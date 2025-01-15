package com.kamcci.numberbox.restapi.scheduler.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileReadCase
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileWriteCase
import com.kamcci.numberbox.restapi.scheduler.member.MemberScheduler.Companion.BATCH_SIZE
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.springframework.scheduling.annotation.Scheduled
import java.lang.reflect.Method

class FileDeleteSchedulerTest {
    private val fileStoragePort: FileStoragePort = mock()
    private val sysGarbageFileReadCase: SysGarbageFileReadCase = mock()
    private val sysGarbageFileWriteCase: SysGarbageFileWriteCase = mock()
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
        val garbageList: MutableList<SysGarbageFileVo> = mutableListOf()
        for (i in 0..100) garbageList.add(SysGarbageFileVo(1L, GarbageFileType.S3, "", "", 0))
        `when`(sysGarbageFileReadCase.readAllByType(GarbageFileType.S3, BATCH_SIZE)).thenReturn(garbageList)

        // when
        fileDeleteScheduler.deleteS3GarbageFile()
    }

    @Test
    fun `삭제 대상 파일 제거 - 성공(배치 사이즈 보다 큰 경우)`() {
        // given
        val garbageList: MutableList<SysGarbageFileVo> = mutableListOf()
        val secGarbageList: MutableList<SysGarbageFileVo> = mutableListOf()
        for (i in 0..600) garbageList.add(SysGarbageFileVo(1L, GarbageFileType.S3, "", "", 0))
        for (i in 0..100) garbageList.add(SysGarbageFileVo(1L, GarbageFileType.S3, "", "", 0))
        `when`(sysGarbageFileReadCase.readAllByType(GarbageFileType.S3, BATCH_SIZE)).thenReturn(garbageList)
            .thenReturn(secGarbageList)

        // when
        fileDeleteScheduler.deleteS3GarbageFile()

        // then
        verify(sysGarbageFileWriteCase, times(2)).deleteById(any())
    }

    @Test
    fun `삭제 대상 파일 제거 - 실패`() {
        // given
        val garbageList: MutableList<SysGarbageFileVo> = mutableListOf()
        garbageList.add(SysGarbageFileVo(1L, GarbageFileType.S3, "", "", 0))
        `when`(sysGarbageFileReadCase.readAllByType(GarbageFileType.S3, BATCH_SIZE)).thenReturn(garbageList)
        `when`(fileStoragePort.delete(any())).thenThrow(RuntimeException(""))

        // when
        fileDeleteScheduler.deleteS3GarbageFile()

        // then -> 성공 횟수 = 0
        val successIdList = argumentCaptor<MutableList<Long>>()
        verify(sysGarbageFileWriteCase).deleteById(successIdList.capture())
        println(successIdList.allValues)
        assert(successIdList.allValues[0].isEmpty())
    }
}
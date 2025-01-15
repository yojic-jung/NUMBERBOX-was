package com.kamcci.numberbox.restapi.scheduler.member

import com.kamcci.numberbox.app.usecase.member.MemberDropCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileWriteCase
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import org.springframework.scheduling.annotation.Scheduled
import java.lang.reflect.Method
import java.util.*

class MemberSchedulerTest {
    private val memberReadCase: MemberReadCase = mock()
    private val memberWriteCase: MemberWriteCase = mock()
    private val memberProfileWriteCase: MemberProfileWriteCase = mock()
    private val memberDropCase: MemberDropCase = mock()
    private val memberScheduler =
        MemberScheduler(memberReadCase, memberWriteCase, memberProfileWriteCase, memberDropCase)

    @Test
    fun `임시 비밀번호 발급자 새로운 비밀번호로 셋팅 실행 6시 설정 - 성공`() {
        // when
        val method: Method = memberScheduler::class.java.getMethod("tmpPasswordChange")
        val scheduledAnnotation = method.getAnnotation(Scheduled::class.java)
        val cronExpression = scheduledAnnotation.cron

        // then
        assertThat(cronExpression).isEqualTo("00 00 06 * * *")
    }

    @Test
    fun `임시 비밀번호 발급자 새로운 비밀번호로 셋팅(배치 사이즈 보다 작음) - 성공`() {
        // given
        val memberIds = listOf(UUID.randomUUID())
        `when`(memberReadCase.readByIsTmpPassword(true, MemberScheduler.BATCH_SIZE)).thenReturn(memberIds)

        // when
        memberScheduler.tmpPasswordChange()

        // then
        verify(memberWriteCase).updateTmpPassword(memberIds)
    }

    @Test
    fun `임시 비밀번호 발급자 새로운 비밀번호로 셋팅(배치 사이즈 보다 큼) - 성공`() {
        // given
        val memberIds: MutableList<UUID> = mutableListOf()
        for (i in 1..600) memberIds.add(UUID.randomUUID())

        val secMemberIds: MutableList<UUID> = mutableListOf()
        for (i in 1..100) secMemberIds.add(UUID.randomUUID())

        `when`(memberReadCase.readByIsTmpPassword(true, MemberScheduler.BATCH_SIZE)).thenReturn(memberIds)
            .thenReturn(secMemberIds)

        // when
        memberScheduler.tmpPasswordChange()

        // then
        verify(memberWriteCase).updateTmpPassword(memberIds)
        verify(memberWriteCase).updateTmpPassword(secMemberIds)
    }

    @Test
    fun `탈퇴 요청 회원 탈퇴처리 6시 10분 설정 - 성공`() {
        // when
        val method: Method = memberScheduler::class.java.getMethod("dropMember")
        val scheduledAnnotation = method.getAnnotation(Scheduled::class.java)
        val cronExpression = scheduledAnnotation.cron

        // then
        assertThat(cronExpression).isEqualTo("00 10 06 * * *")
    }


    @Test
    fun `탈퇴 요청 회원 탈퇴처리 - 성공`() {
        // given
        val uuid = UUID.randomUUID()
        `when`(memberReadCase.readUserIdByHumanStatus(3)).thenReturn(listOf(uuid))

        // when
        memberScheduler.dropMember()

        // then
        verify(memberDropCase).drop(uuid)
    }

    @Test
    fun `탈퇴 요청 회원 탈퇴처리 - 실패`() {
        // given
        val uuid = UUID.randomUUID()
        `when`(memberReadCase.readUserIdByHumanStatus(3)).thenReturn(listOf(uuid))
        `when`(memberDropCase.drop(uuid)).thenThrow(RuntimeException(""))

        // when & then
        assertDoesNotThrow {
            memberScheduler.dropMember()
        }
    }


    @Test
    fun `일일 학습지 다운로드 횟수 초기화 00시 설정 - 성공`() {
        // when
        val method: Method = memberScheduler::class.java.getMethod("initHwpDownCnt")
        val scheduledAnnotation = method.getAnnotation(Scheduled::class.java)
        val cronExpression = scheduledAnnotation.cron

        // then
        assertThat(cronExpression).isEqualTo("00 00 00 * * *")
    }

    @Test
    fun `일일 학습지 다운로드 횟수 초기화 - 성공`() {
        // when
        memberScheduler.initHwpDownCnt()

        // then
        verify(memberProfileWriteCase).updateHwpDownCnt(0)
    }
}
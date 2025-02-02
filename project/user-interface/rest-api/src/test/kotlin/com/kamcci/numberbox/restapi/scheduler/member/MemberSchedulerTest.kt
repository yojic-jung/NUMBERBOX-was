package com.kamcci.numberbox.restapi.scheduler.member

import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberDropCase
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberProfileWriteCase
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberReadCase
import com.kamcci.numberbox.app.service.stub.usecase.member.MockMemberWriteCase
import com.kamcci.numberbox.app.usecase.member.MemberDropCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileWriteCase
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.scheduling.annotation.Scheduled
import java.lang.reflect.Method

class MemberSchedulerTest {
    private val memberReadCase: MemberReadCase = MockMemberReadCase()
    private val memberWriteCase: MemberWriteCase = MockMemberWriteCase()
    private val memberProfileWriteCase: MemberProfileWriteCase = MockMemberProfileWriteCase()
    private val memberDropCase: MemberDropCase = MockMemberDropCase()
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
        val mockMemberReadCase = MockMemberReadCase()
        val memberScheduler = MemberScheduler(
            mockMemberReadCase,
            MockMemberWriteCase(),
            MockMemberProfileWriteCase(),
            MockMemberDropCase(),
        )

        // 배치 사이즈보다 작게 실행
        mockMemberReadCase.moreBatchSize = false


        // when
        memberScheduler.tmpPasswordChange()

        // then
        assertThat(mockMemberReadCase.executeCnt).isEqualTo(1)
    }

    @Test
    fun `임시 비밀번호 발급자 새로운 비밀번호로 셋팅(배치 사이즈 보다 큼) - 성공`() {
        // given
        val mockMemberReadCase = MockMemberReadCase()
        val memberScheduler = MemberScheduler(
            mockMemberReadCase,
            MockMemberWriteCase(),
            MockMemberProfileWriteCase(),
            MockMemberDropCase(),
        )
        // 배치 사이즈보다 크게 실행
        mockMemberReadCase.moreBatchSize = true

        // when
        memberScheduler.tmpPasswordChange()

        // then
        assertThat(mockMemberReadCase.executeCnt).isEqualTo(2)
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
        val dropCase = MockMemberDropCase()
        val memberScheduler = MemberScheduler(
            MockMemberReadCase(),
            MockMemberWriteCase(),
            MockMemberProfileWriteCase(),
            dropCase,
        )

        // when
        memberScheduler.dropMember()

        // then
    }

    @Test
    fun `탈퇴 요청 회원 탈퇴처리 - 실패`() {
        // given
        val dropCase = MockMemberDropCase()
        val memberScheduler = MemberScheduler(
            MockMemberReadCase(),
            MockMemberWriteCase(),
            MockMemberProfileWriteCase(),
            dropCase,
        )
        dropCase.isExceptionCase = true
        // when
        memberScheduler.dropMember()

        // then
        assertThat(dropCase.executeCnt).isEqualTo(0)
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
        // when & then
        assertDoesNotThrow {
            memberScheduler.initHwpDownCnt()
        }
    }
}
package com.kamcci.numberbox.restapi.scheduler.member

import com.kamcci.numberbox.app.usecase.member.MemberDropCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileWriteCase
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MemberScheduler(
    private val memberReadCase: MemberReadCase,
    private val memberWriteCase: MemberWriteCase,
    private val memberProfileWriteCase: MemberProfileWriteCase,
    private val memberDropCase: MemberDropCase
) {
    companion object {
        const val BATCH_SIZE = 500L
    }

    private val log = LoggerFactory.getLogger(javaClass)

    // 임시 비밀번호 발급자 새로운 비밀번호로 셋팅
    @Scheduled(cron = "00 00 06 * * *")
    fun tmpPasswordChange() {
        // 임시 비밀번호 발급시 메일로 06시에 초기화된다고 공지함
        while (true) {
            val memberIds = memberReadCase.readByIsTmpPassword(true, BATCH_SIZE)
            memberWriteCase.updateTmpPassword(memberIds)

            if (memberIds.size < BATCH_SIZE) break
        }
    }

    // 탈퇴 요청 회원 탈퇴처리
    @Scheduled(cron = "00 10 06 * * *")
    fun dropMember() {
        // 회원 탈퇴 요청 대상자 조회(관리자, 매니저 제외)
        val dropReqIds = memberReadCase.readUserIdByHumanStatus(3)
        dropReqIds.forEach { memberId ->
            try {
                memberDropCase.drop(memberId)
            } catch (e: Exception) {
                log.info("회원 탈퇴 실패 : $memberId, ${e.message}")
            }
        }
    }

    // 일일 학습지 다운로드 횟수 초기화
    @Scheduled(cron = "00 00 00 * * *")
    fun initHwpDownCnt() {
        memberProfileWriteCase.updateHwpDownCnt(0)
    }

    // todo 만료된 리프레시 토큰 삭제
//    @Scheduled(cron = "00 00 00 * * *")
//    fun deleteExpiredRefreshToken() {
//    }

}
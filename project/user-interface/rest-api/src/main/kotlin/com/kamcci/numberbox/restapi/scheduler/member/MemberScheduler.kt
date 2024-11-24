package com.kamcci.numberbox.restapi.scheduler.member

import com.kamcci.numberbox.app.usecase.member.MemberProfileWriteCase
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MemberScheduler(
    private val memberReadCase: MemberReadCase,
    private val memberWriteCase: MemberWriteCase,
    private val memberProfileWriteCase: MemberProfileWriteCase
) {
    companion object {
        const val BATCH_SIZE = 500L
    }

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
        /**
         * 1. 개인정보 삭제 처리
         * 2. 휴먼 계정 처리
         * 3. 제작 컨텐츠 비공개 처리
         */

        // 회원 탈퇴 요청 대상자 조회(관리자, 매니저 제외)

//        memberModifyUseCase.drop()
    }

    // 일일 학습지 다운로드 횟수 초기화
    @Scheduled(cron = "00 00 00 * * *")
    fun initHwpDownCnt() {
        memberProfileWriteCase.updateHwpDownCnt(0)
    }

    // 만료된 리프레시 토큰 삭제
    @Scheduled(cron = "00 00 00 * * *")
    fun deleteExpiredRefreshToken() {
        // todo
    }

}
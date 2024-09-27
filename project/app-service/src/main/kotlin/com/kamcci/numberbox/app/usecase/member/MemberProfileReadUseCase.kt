package com.kamcci.numberbox.app.usecase.member

/**
 * 프로필 조회
 */
interface MemberProfileReadUseCase {
    // 프로필 가져오기
    fun findByMemberId()

    // 프로필 이미지 가져오기
    fun findProfileImgByMemberId()
}
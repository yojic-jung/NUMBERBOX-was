package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.usecase.member.MemberFollowWriteCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * 회원 팔로우 및 팔로워 - 변경
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/member/following")
class MembersFollowWriteController(
    private val memberFollowWriteCase: MemberFollowWriteCase,
    private val memberProfileReadCase: MemberProfileReadCase
) {
    companion object {
        const val PROFILE_NOT_EXIST = "해당 계정의 프로필이 존재하지 않습니다."
    }

    /**
     * 팔로잉
     */
    @PostMapping("/{profileId}")
    fun following(
        @PathVariable profileId: Long,
        @UserId memberId: UUID
    ): ResponseEntity<ResponseData<String>> {
        // 팔로잉 하기
        val myProfileId = memberProfileReadCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessInValidException(PROFILE_NOT_EXIST)
        memberFollowWriteCase.following(profileId, myProfileId)
        return ResponseUtil.ok("")
    }

    /**
     * 팔로잉 취소
     */
    @DeleteMapping("/{profileId}")
    fun cancel(
        @PathVariable profileId: Long,
        @UserId memberId: UUID
    ): ResponseEntity<ResponseData<String>> {
        // 팔로잉 취소
        val myProfileId = memberProfileReadCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessInValidException(PROFILE_NOT_EXIST)
        memberFollowWriteCase.cancel(profileId, myProfileId)
        return ResponseUtil.ok()
    }
}
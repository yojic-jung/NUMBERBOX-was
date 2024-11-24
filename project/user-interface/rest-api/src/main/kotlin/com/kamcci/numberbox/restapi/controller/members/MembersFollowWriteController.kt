package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadCase
import com.kamcci.numberbox.app.usecase.member.MemberFollowWriteCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/member/following")
class MembersFollowWriteController(
    private val memberFollowWriteCase: MemberFollowWriteCase,
    private val memberFollowReadCase: MemberFollowReadCase,
    private val memberProfileReadCase: MemberProfileReadCase
) {
    /**
     * 팔로잉
     */
    @PostMapping("/{profileId}")
    fun following(
        @PathVariable profileId: Long,
        @UserId memberId: UUID
    ): ResponseEntity<ResponseData<Map<String, Any>>> {
        // 팔로잉 하기
        val myProfileId = memberProfileReadCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessValidException("해당 계정의 프로필이 존재하지 않습니다.")
        memberFollowWriteCase.following(profileId, myProfileId)

        // 해당 사용자의 팔로워 수
        val followerCnt = memberFollowReadCase.countFollower(profileId)
        return ResponseUtil.ok(mapOf("followerCnt" to followerCnt))
    }

    /**
     * 팔로잉 취소
     */
    @DeleteMapping("/{profileId}")
    fun cancel(
        @PathVariable profileId: Long,
        @UserId memberId: UUID
    ): ResponseEntity<ResponseData<Map<String, Any>>> {
        // 팔로잉 취소
        val myProfileId = memberProfileReadCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessValidException("해당 계정의 프로필이 존재하지 않습니다.")
        memberFollowWriteCase.cancel(profileId, myProfileId)

        // 해당 사용자의 팔로워 수
        val followerCnt = memberFollowReadCase.countFollower(profileId)
        return ResponseUtil.ok(mapOf("isSuccess" to followerCnt))
    }
}
package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.member.MemberFollowModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/member/following")
class MembersFollowController(
    private val memberFollowModifyUseCase: MemberFollowModifyUseCase,
    private val memberFollowReadUseCase: MemberFollowReadUseCase,
    private val memberProfileReadUseCase: MemberProfileReadUseCase
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
        val myProfileId = memberProfileReadUseCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessValidException("해당 계정의 프로필이 존재하지 않습니다.")
        memberFollowModifyUseCase.following(profileId, myProfileId)

        // 해당 사용자의 팔로워 수
        val followerCnt = memberFollowReadUseCase.countFollower(profileId)
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
        val myProfileId = memberProfileReadUseCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessValidException("해당 계정의 프로필이 존재하지 않습니다.")
        memberFollowModifyUseCase.cancel(profileId, myProfileId)

        // 해당 사용자의 팔로워 수
        val followerCnt = memberFollowReadUseCase.countFollower(profileId)
        return ResponseUtil.ok(mapOf("isSuccess" to followerCnt))
    }
}
package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.usecase.member.MemberFollowModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/member/follow")
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
        val myProfileId = memberProfileReadUseCase.findProfileIdByMemberId(memberId)
            ?: throw BusinessInValidException("해당 계정의 프로필이 존재하지 않습니다.")
        val isSuccess = memberFollowModifyUseCase.following(profileId, myProfileId)

        // 나의 팔로워 수
        val count = memberFollowReadUseCase.countFollower(myProfileId)
        return ResponseUtil.ok(
            mapOf(
                "isSuccess" to isSuccess,
                "count" to count
            )
        )
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
        val myProfileId = memberProfileReadUseCase.findProfileIdByMemberId(memberId)
            ?: throw BusinessInValidException("해당 계정의 프로필이 존재하지 않습니다.")
        val isSuccess = memberFollowModifyUseCase.cancel(profileId, myProfileId)
        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
    }
}
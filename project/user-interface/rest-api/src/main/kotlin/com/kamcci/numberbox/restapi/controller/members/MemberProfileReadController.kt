package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.constraints.Positive
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/member/profile")
class MemberProfileReadController(
    private val memberProfileReadCase: MemberProfileReadCase,
    private val memberFollowReadCase: MemberFollowReadCase,
) {
    companion object {
        const val PROFILE_NOT_EXIST = "해당 계정의 프로필이 존재하지 않습니다."
    }

    /**
     * 내 프로필 보기
     */
    @GetMapping("")
    fun readMyProfile(
        @UserId memberId: UUID
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        // 1. 프로필 조회
        val myProfile = memberProfileReadCase.readByMemberId(memberId)

        // 2. 팔로잉 프로필 조회
        val followingProfile = memberProfileReadCase.readFollowingProfileByMemberId(memberId)

        // 3. 팔로워 프로필 조회
        val followerProfile = memberProfileReadCase.readFollowerProfileByMemberId(memberId)

        return ResponseUtil.ok(
            mapOf(
                "myProfile" to myProfile,
                "followingProfile" to followingProfile,
                "followingCnt" to followingProfile.size,
                "followerProfile" to followerProfile,
                "followerCnt" to followerProfile.size,
            )
        )
    }

    /**
     * 다른 사람 프로필 보기
     */
    @GetMapping("/{profileId}")
    fun readUserProfile(
        @Positive(message = "프로필 아이디는 양수만 가능합니다.")
        @PathVariable profileId: Long,
        @UserId memberId: UUID
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        // 1. 프로필 조회
        val profile = memberProfileReadCase.readByProfileId(profileId)

        // 2. 팔로우 여부 조회
        val myProfileId = memberProfileReadCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessValidException(PROFILE_NOT_EXIST)
        val isMyFollower = memberFollowReadCase.isFollowing(profileId, myProfileId)

        // 3. 팔로워 수 조회
        val followerCount = memberFollowReadCase.countFollower(profileId)
        return ResponseUtil.ok(
            mapOf(
                "profile" to profile,
                "isFollowing" to isMyFollower,
                "followerCnt" to followerCount,
            )
        )
    }
}
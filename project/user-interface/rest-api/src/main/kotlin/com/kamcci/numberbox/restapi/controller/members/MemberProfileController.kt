package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import com.kamcci.numberbox.restapi.dto.request.member.ProfileImgUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.ProfileNicknameUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.ProfileTypeUpdtRequest
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/member/profile")
class MemberProfileController(
    private val memberProfileReadUseCase: MemberProfileReadUseCase,
    private val memberProfileModifyUseCase: MemberProfileModifyUseCase,
    private val memberFollowReadUseCase: MemberFollowReadUseCase,
    private val memberMapper: MemberMapper
) {
    // 프로필 등록
    @PutMapping("")
    fun updateProfile(
        @UserId memberId: UUID,
        @RequestBody @Valid
        profileImgReq: ProfileTypeUpdtRequest
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        val isRegistered = memberProfileModifyUseCase.updateProfileTypeByMemberId(memberId, profileImgReq.profileType)
        return ResponseUtil.ok(mapOf("isRegistered" to isRegistered))
    }


    // 프로필 이미지 등록
    @PutMapping("/img")
    fun updateProfileImg(
        @UserId memberId: UUID,
        @ModelAttribute @Valid
        profileImgReq: ProfileImgUpdtRequest
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        // 프로필 이미지 변경
        val fileNameVo = memberProfileModifyUseCase.updateImgByMemberId(
            memberId,
            profileImgReq.imgFile.originalFilename!!,
            profileImgReq.imgFile.inputStream
        )
        return ResponseUtil.ok(mapOf("fileNameVo" to fileNameVo))
    }

    // 닉네임 변경
    @PutMapping("/nickname")
    fun updateNickname(
        @UserId memberId: UUID,
        @RequestBody @Valid
        profileNicknameReq: ProfileNicknameUpdtRequest
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        val isUpdated =
            memberProfileModifyUseCase.updateNicknameByMemberId(memberId, profileNicknameReq.nickname)
        return ResponseUtil.ok(mapOf("isUpdated" to isUpdated))
    }

    // 내 프로필 보기
    @GetMapping("")
    fun readMyProfile(
        @UserId memberId: UUID
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        // 1. 프로필 조회
        val myProfile = memberProfileReadUseCase.readByMemberId(memberId)
        val myProfileRs = memberMapper.toProfileResponse(myProfile)

        // 2. 팔로잉 프로필 조회
        val followingProfile = memberProfileReadUseCase.readFollowingProfileByMemberId(memberId)
        val followingProfileRs = memberMapper.toProfileResponse(followingProfile)

        // 3. 팔로워 프로필 조회
        val followerProfile = memberProfileReadUseCase.readFollowerProfileByMemberId(memberId)
        val followerProfileRs = memberMapper.toProfileResponse(followerProfile)

        return ResponseUtil.ok(
            mapOf(
                "myProfile" to myProfileRs,
                "followingProfile" to followingProfileRs,
                "followingCnt" to followingProfileRs.size,
                "followerProfile" to followerProfileRs,
                "followerCnt" to followerProfileRs.size,
            )
        )
    }

    // 다른 사람 프로필 보기
    @GetMapping("/{profileId}")
    fun readUserProfile(
        @Positive(message = "프로필 아이디는 양수만 가능합니다.")
        @PathVariable profileId: Long,
        @UserId memberId: UUID
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        // 1. 프로필 조회
        val profile = memberProfileReadUseCase.readByProfileId(profileId)

        // 2. 팔로우 여부 조회
        val myProfileId = memberProfileReadUseCase.readProfileIdByMemberId(memberId)
            ?: throw BusinessValidException("해당 계정의 프로필이 존재하지 않습니다.")
        val isMyFollower = memberFollowReadUseCase.isFollowing(profileId, myProfileId)

        // 3. 팔로워 수 조회
        val followerCount = memberFollowReadUseCase.countFollower(profileId)
        return ResponseUtil.ok(
            mapOf(
                "profile" to profile,
                "isFollowing" to isMyFollower,
                "followerCnt" to followerCount,
            )
        )
    }
}
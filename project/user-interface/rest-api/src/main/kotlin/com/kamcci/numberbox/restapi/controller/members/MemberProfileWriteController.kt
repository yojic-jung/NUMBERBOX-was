package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileWriteCase
import com.kamcci.numberbox.restapi.dto.request.member.ProfileImgUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.ProfileNicknameUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.ProfileTypeUpdtRequest
import com.kamcci.numberbox.restapi.util.file.FileUtil.toFile
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/member/profile")
class MemberProfileWriteController(
    private val memberProfileWriteCase: MemberProfileWriteCase,
    private val fileUseCase: FileUseCase,
) {
    /**
     * 프로필 등록
     */
    @PutMapping("")
    fun updateProfile(
        @UserId memberId: UUID,
        @RequestBody @Valid
        profileImgReq: ProfileTypeUpdtRequest
    ): ResponseEntity<ResponseData<String>> {
        memberProfileWriteCase.updateProfileTypeByMemberId(memberId, profileImgReq.profileType)
        return ResponseUtil.ok()
    }


    /**
     * 프로필 이미지 등록
     */
    @PutMapping("/img")
    fun updateProfileImg(
        @UserId memberId: UUID,
        @ModelAttribute @Valid
        req: ProfileImgUpdtRequest
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        // 파일 업로드
        val fileNameVo = fileUseCase.upload(toFile(req.imgFile), FileType.ProfileIMG)
        val updateDto = MemberProfileImgUpdtDto(memberId, fileNameVo.path, fileNameVo.name)

        //  프로필 저장
        memberProfileWriteCase.updateImgByMemberId(updateDto)
        return ResponseUtil.ok(mapOf("fileNameVo" to fileNameVo))
    }

    /**
     * 닉네임 변경
     */
    @PutMapping("/nickname")
    fun updateNickname(
        @UserId memberId: UUID,
        @RequestBody @Valid
        profileNicknameReq: ProfileNicknameUpdtRequest
    ): ResponseEntity<ResponseData<String>> {
        memberProfileWriteCase.updateNicknameByMemberId(memberId, profileNicknameReq.nickname)
        return ResponseUtil.ok()
    }

}
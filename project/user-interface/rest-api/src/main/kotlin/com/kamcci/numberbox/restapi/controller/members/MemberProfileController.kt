package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.usecase.member.MemberProfileModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member/profile")
class MemberProfileController(
    private val memberProfileReadUseCase: MemberProfileReadUseCase,
    private val memberProfileModifyUseCase: MemberProfileModifyUseCase,
) {

    // 프로필 등록
    @PutMapping("")
    fun profileRegister() {

    }

    // 프로필 이미지 등록
    @PutMapping("/img")
    fun profileImgRegister() {

    }

    // 닉네임 변경
    @PutMapping("/nickname")
    fun modifyNickname() {

    }

    // 내 프로필 보기
    @GetMapping("")
    fun myProfile() {

    }

    // 다른 사람 프로필 보기
    @GetMapping("/{profileId}")
    fun profile(@PathVariable profileId: Long) {

    }
}
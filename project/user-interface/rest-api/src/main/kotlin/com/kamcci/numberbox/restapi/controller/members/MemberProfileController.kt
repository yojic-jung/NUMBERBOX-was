package com.kamcci.numberbox.restapi.controller.members

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/myInfo")
class MemberProfileController {

    // 프로필 등록
    @PostMapping("")
    fun profileRegister() {

    }

    // 프로필 이미지 등록
    @PutMapping("")
    fun profileImgRegister() {

    }

    // 닉네임 변경
    @PutMapping("")
    fun modifyNickname() {

    }

    // 내 프로필 보기
    @GetMapping("")
    fun myProfile() {

    }

    // 다른 사람 프로필 보기
    @GetMapping("")
    fun profile(@PathVariable profileId: Long) {

    }
}
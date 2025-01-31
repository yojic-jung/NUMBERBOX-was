package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.usecase.member.MemberFindReadCase
import com.kamcci.numberbox.restapi.dto.request.member.EmailFindRequest
import com.kamcci.numberbox.restapi.dto.request.member.PasswordFindRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/public/member")
class MemberFindController(
    private val memberFindReadCase: MemberFindReadCase,
) {
    /**
     * 이메일 찾기
     */
    @GetMapping("/findEmail")
    fun findEmail(
        @Valid
        @ModelAttribute req: EmailFindRequest
    ): ResponseEntity<ResponseData<Map<String, String?>>> {
        return ResponseUtil.ok(mapOf("email" to memberFindReadCase.readMyEmail(req.userName, req.phoneNumber)))
    }

    /**
     * 임시 비밀번호 발급
     */
    @PutMapping("/findPassword")
    fun findPassword(
        @Valid @RequestBody req: PasswordFindRequest
    ): ResponseEntity<ResponseData<Map<String, Boolean>>> {
        memberFindReadCase.sendNewTempPassword(req.email)
        return ResponseUtil.ok(mapOf("isSuccess" to true))
    }
}
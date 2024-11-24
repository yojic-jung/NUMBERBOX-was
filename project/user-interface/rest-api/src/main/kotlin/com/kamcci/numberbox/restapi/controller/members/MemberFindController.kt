package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.usecase.member.MemberFindUseCase
import com.kamcci.numberbox.restapi.dto.request.member.EmailFindRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import com.kamcci.numberbox.restapi.validation.member.EmailCheck
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/public/member")
class MemberFindController(
    private val memberFindUseCase: MemberFindUseCase,
) {
    /**
     * 이메일 찾기
     */
    @GetMapping("/findEmail")
    fun findEmail(
        @Valid
        @ModelAttribute req: EmailFindRequest
    ): ResponseEntity<ResponseData<Map<String, String?>>> {
        return ResponseUtil.ok(mapOf("email" to memberFindUseCase.readMyEmail(req.userName, req.phoneNumber)))
    }

    /**
     * 비밀번호 찾기
     */
    @GetMapping("/findPassword")
    fun findPassword(
        @EmailCheck
        @RequestParam email: String
    ): ResponseEntity<ResponseData<Map<String, Boolean>>> {
        memberFindUseCase.readMyPassword(email)
        return ResponseUtil.ok(mapOf("isSuccess" to true))
    }
}
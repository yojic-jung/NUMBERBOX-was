package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserEmail
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/member")
class MemberReadController(
    private val memberWriteCase: MemberWriteCase,
    private val memberMapper: MemberMapper,
) {
    // 내 이메일
    @GetMapping("/email")
    fun readEmail(@UserEmail email: String): ResponseEntity<ResponseData<Map<String, Any?>>> {
        return ResponseUtil.ok(mapOf("email" to email))
    }

}
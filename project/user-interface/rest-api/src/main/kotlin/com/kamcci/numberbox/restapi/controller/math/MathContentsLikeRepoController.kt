package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/math/like-repo/content")
class MathContentsLikeRepoController {
    // 저장소에 문제 저장
    @GetMapping("")
    fun isLikeRepoContents(
        @UserId userId: UUID,
    ): ResponseEntity<ResponseData<Any>> {
        return ResponseUtil.ok(true)
    }
}
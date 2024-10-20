package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.math.MathContentsReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathUnitInfoReadUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import com.kamcci.numberbox.restapi.dto.request.common.ValidPageRequest
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsCreateRequest
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsSearchRequest
import com.kamcci.numberbox.restapi.util.math.MathUnitUtil.getUnitIdList
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/math/content")
class MathContentsController(
    private val memberProfileReadUseCase: MemberProfileReadUseCase,
    private val mathUnitInfoReadUseCase: MathUnitInfoReadUseCase,
    private val mathContentsReadUseCase: MathContentsReadUseCase,
    private val mathContentsRepoReadUseCase: MathContentsRepoReadUseCase,
) {
    companion object {
        const val NOT_EXIST_MEMBER = "존재하지 않는 계정입니다."
        const val NOT_EXIST_CONTENTS = "존재하지 않는 수학 문제입니다."
    }

    // 문제 등록
    @PostMapping("")
    fun makeContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathContentsCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        return ResponseUtil.ok(mapOf("contents" to "res"))
    }


    // 문제 번호로 조회
    @GetMapping("/{contentsId}")
    fun getContentsById(
        @UserId memberId: UUID,
        @PathVariable contentsId: Long
    ): ResponseEntity<ResponseData<Any>> {
        // 문제 조회
        val res =
            mathContentsReadUseCase.findByContentsId(contentsId) ?: throw BusinessValidException(NOT_EXIST_CONTENTS)

        return ResponseUtil.ok(mapOf("contents" to res))
    }


    // 나의 문제
    @GetMapping("/user")
    fun myContents(
        @UserId memberId: UUID,
        @ModelAttribute
        @Valid req: ValidPageRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum ?: 0, req.pageVolume ?: 100)
        val res = mathContentsReadUseCase.findByMemberId(memberId, pageReq)

        return ResponseUtil.ok(mapOf("contents" to res))
    }

    // 사용자 문제
    @GetMapping("/user/{profileId}")
    fun userContents(
        @PathVariable profileId: Long,
        @ModelAttribute
        @Valid req: ValidPageRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 프로필 조회
        val profile =
            memberProfileReadUseCase.findByProfileId(profileId) ?: throw BusinessValidException(NOT_EXIST_MEMBER)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum ?: 0, req.pageVolume ?: 100)
        val res = mathContentsReadUseCase.findByProfileId(profileId, pageReq)

        return ResponseUtil.ok(
            mapOf(
                "profile" to profile,
                "contents" to res,
            )
        )
    }

    // 문제 조회
    @GetMapping("/list")
    fun read(
        @UserId memberId: UUID,
        @ModelAttribute
        @Valid req: MathContentsSearchRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 검색할 단원 id 추출
        val unitInfoList = mathUnitInfoReadUseCase.findAll()
        val unitIdList: List<Int> = getUnitIdList(unitInfoList, req.searchType, req.unitId)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum ?: 0, req.pageVolume ?: 100)
        val res = mathContentsReadUseCase.findByUnitId(memberId, unitIdList, pageReq)

        return ResponseUtil.ok(mapOf("contents" to res))
    }

    // 내 저장소 문제 조회
    @GetMapping("/repo")
    fun myRepoContents(
        @UserId memberId: UUID,
        @ModelAttribute @Valid req: ValidPageRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 저장소에 등록된 문제 id 목록 조회
        val contentsIdList = mathContentsRepoReadUseCase.findContentsIdByMemberId(memberId)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum ?: 0, req.pageVolume ?: 100)
        val res = mathContentsReadUseCase.findByContentsId(contentsIdList, pageReq)
        return ResponseUtil.ok(mapOf("contents" to res))
    }

}
package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType.InHouse
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType.Ipsi
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.vo.math.MathContentsOnlyVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathInHouseContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathIpsiContentsVo
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadCase
import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import com.kamcci.numberbox.restapi.dto.request.common.ValidPageRequest
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsSearchRequest
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kamcci.numberbox.restapi.util.math.MathUnitUtil.getUnitIdList
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/content")
class MathContentsReadController(
    private val memberProfileReadCase: MemberProfileReadCase,
    private val mathCategoryUnitReadCase: MathCategoryUnitReadCase,
    private val mathContentsReadCase: MathContentsReadCase,
    private val mathContentsRepoReadCase: MathContentsRepoReadCase,
    // 매퍼
    private val memberMapper: MemberMapper
) {
    companion object {
        const val NOT_EXIST_MEMBER = "존재하지 않는 계정입니다."
        const val NOT_EXIST_CONTENTS = "존재하지 않는 수학 문제입니다."
    }

    // 문제 id로 조회
    @GetMapping("/{contentsId}")
    fun getContentsById(
        @UserId memberId: UUID,
        @PathVariable contentsId: Long,
        @RequestParam contentsOnly: Boolean?,
        @RequestParam contentsClassify: ContentsClassifyType,
    ): ResponseEntity<ResponseData<Any>> {
        // 문제 조회
        val res =
            when {
                // 문제만 조회
                contentsOnly != null && contentsOnly -> mathContentsReadCase.readContentsOnly(contentsId, memberId)

                // 자체제작 문제는 유사문제 정보
                contentsClassify == InHouse -> mathContentsReadCase.readInHouseContentsById(contentsId)

                // 입시 문제는 입시 출처 정보
                contentsClassify == Ipsi -> mathContentsReadCase.readIpsiContentsById(contentsId)

                // 그외는 라이선스 정보
                else -> mathContentsReadCase.readById(contentsId)
            } ?: throw BusinessValidException(NOT_EXIST_CONTENTS)

        // 나의 제작문제인지 판별
        val isMine =
            when {
                contentsOnly != null && contentsOnly -> {
                    res as MathContentsOnlyVo
                    res.memberId == memberId
                }
                // 자체제작 문제는 유사문제 정보
                contentsClassify == InHouse -> {
                    res as MathInHouseContentsVo
                    res.memberId == memberId
                }

                // 입시 문제는 입시 출처 정보
                contentsClassify == Ipsi -> {
                    res as MathIpsiContentsVo
                    res.memberId == memberId
                }

                // 그외는 라이선스 정보
                else -> {
                    res as MathContentsVo
                    res.memberId == memberId
                }

            }

        return ResponseUtil.ok(
            mapOf(
                "contents" to res,
                "isMine" to isMine
            )
        )
    }


    // 나의 문제
    @GetMapping("/my")
    fun readMyContents(
        @UserId memberId: UUID,
        @ModelAttribute
        @Valid req: ValidPageRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum, req.pageVolume)
        val res = mathContentsReadCase.readDetailByMemberId(memberId, ContentsSvcPosbSttsType.Release, pageReq)

        return ResponseUtil.ok(mapOf("contents" to res))
    }

    // 사용자 문제
    @GetMapping("/user/{profileId}")
    fun readUserContents(
        @UserId
        myMemberId: UUID,
        @PathVariable profileId: Long,
        @ModelAttribute
        @Valid req: ValidPageRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 프로필 조회
        val profile =
            memberProfileReadCase.readByProfileId(profileId) ?: throw BusinessValidException(NOT_EXIST_MEMBER)
        val profileRes = memberMapper.toProfileResponse(profile)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum, req.pageVolume)
        val res =
            mathContentsReadCase.readDetailByMemberId(
                profile.memberId,
                myMemberId,
                ContentsSvcPosbSttsType.Release,
                pageReq
            )

        return ResponseUtil.ok(
            mapOf(
                "profile" to profileRes,
                "contents" to res,
            )
        )
    }

    // 문제 조회
    @GetMapping("/list")
    fun readList(
        @UserId memberId: UUID,
        @ModelAttribute
        @Valid req: MathContentsSearchRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 검색할 단원 id 추출
        val unitInfoList = mathCategoryUnitReadCase.readAll()
        val unitIdList: List<Int> = getUnitIdList(unitInfoList, req.searchType, req.unitId)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum, req.pageVolume)
        val res = mathContentsReadCase.readDetailByUnitId(memberId, unitIdList, pageReq)

        return ResponseUtil.ok(mapOf("contents" to res))
    }

    // 내 저장소 문제 조회
    @GetMapping("/repo")
    fun readMyRepoContents(
        @UserId memberId: UUID,
        @ModelAttribute @Valid req: ValidPageRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 저장소에 등록된 문제 id 목록 조회
        val contentsIdList = mathContentsRepoReadCase.readContentsIdByMemberId(memberId)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum, req.pageVolume)
        val res = mathContentsReadCase.readById(contentsIdList, pageReq)
        return ResponseUtil.ok(mapOf("contents" to res))
    }

}
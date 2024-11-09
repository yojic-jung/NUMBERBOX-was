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
import com.kamcci.numberbox.app.usecase.math.*
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadUseCase
import com.kamcci.numberbox.restapi.dto.request.common.ValidPageRequest
import com.kamcci.numberbox.restapi.dto.request.math.*
import com.kamcci.numberbox.restapi.mapper.math.MathContentsMapper
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
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
    // 문제 조회 목적
    private val memberProfileReadUseCase: MemberProfileReadUseCase,
    private val mathUnitInfoReadUseCase: MathUnitInfoReadUseCase,
    private val mathContentsReadUseCase: MathContentsReadUseCase,
    private val mathContentsRepoReadUseCase: MathContentsRepoReadUseCase,
    // 문제 제작 목적
    private val mathContentsModifyUseCase: MathContentsModifyUseCase,
    private val mathConGrammarModifyUseCase: MathContentsGrammarModifyUseCase,
    private val mathContentsMapper: MathContentsMapper,
    // 매퍼
    private val memberMapper: MemberMapper
) {
    companion object {
        const val NOT_EXIST_MEMBER = "존재하지 않는 계정입니다."
        const val NOT_EXIST_CONTENTS = "존재하지 않는 수학 문제입니다."
    }

    // 사용자 제작 문제 등록
    @PostMapping("/user-custom")
    fun createUserCustomContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConLicenseCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        val contentsId = mathContentsModifyUseCase.createUserCustomContents(contents, createReq.license)

        // 생성된 문제 정보 반환
        return ResponseUtil.ok(
            mapOf(
                "contents" to mathContentsReadUseCase.readDetailByContentsIdAndMemberId(
                    contentsId,
                    memberId
                )
            )
        )
    }

    // 사용자 제작 문제 수정
    @PutMapping("/user-custom")
    fun updateUserCustomContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConLicenseUpdtRequest
    ): ResponseEntity<ResponseData<Any>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        mathContentsModifyUseCase.updateUserCustomContents(createReq.contentsId, contents, createReq.license).let {
            if (!it) throw BusinessValidException("수학문제가 수정 되지 않았습니다.")
        }

        // 생성된 문제 정보 반환
        return ResponseUtil.ok(
            mapOf(
                "contents" to mathContentsReadUseCase.readDetailByContentsIdAndMemberId(
                    createReq.contentsId,
                    memberId
                )
            )
        )
    }

    // 변형문제 등록
    @PostMapping("/trans")
    fun createransContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConTransCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        val contentsId = mathContentsModifyUseCase.createTransContents(createReq.orgContentsId, contents)

        // 생성된 문제 정보 반환
        return ResponseUtil.ok(
            mapOf(
                "contents" to mathContentsReadUseCase.readDetailByContentsIdAndMemberId(
                    contentsId,
                    memberId
                )
            )
        )
    }

    // 변형문제 수정
    @PutMapping("/trans")
    fun updateTransContents(
        @UserId memberId: UUID,
        @RequestBody
        @Valid createReq: MathConTransUpdtRequest
    ): ResponseEntity<ResponseData<Any>> {
        // request to dto 변환
        val contents = mathContentsMapper.toContents(memberId, createReq.contents)

        // 수학문제 생성
        mathContentsModifyUseCase.updateTransContents(createReq.contentsId, contents)

        // 생성된 문제 정보 반환
        return ResponseUtil.ok(
            mapOf(
                "contents" to mathContentsReadUseCase.readDetailByContentsIdAndMemberId(
                    createReq.contentsId,
                    memberId
                )
            )
        )
    }

    // 문제 문법 등록
    @PostMapping("/grammar")
    fun createMathGrammer(
        @UserId memberId: UUID,
        @RequestBody
        @Valid modifyReq: MathContestGrammarModifyRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 문법 등록
        mathConGrammarModifyUseCase.createGrammar(modifyReq.contentsId, modifyReq.grammar)
        // 생성된 문제 정보 반환
        return ResponseUtil.ok(mapOf("success" to true))
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
                contentsOnly != null && contentsOnly -> mathContentsReadUseCase.readContentsOnly(contentsId, memberId)

                // 자체제작 문제는 유사문제 정보
                contentsClassify == InHouse -> mathContentsReadUseCase.readInHouseContentsById(contentsId)

                // 입시 문제는 입시 출처 정보
                contentsClassify == Ipsi -> mathContentsReadUseCase.readIpsiContentsById(contentsId)

                // 그외는 라이선스 정보
                else -> mathContentsReadUseCase.readById(contentsId)
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
        val pageReq = PageRequestImpl(req.pageNum ?: 0, req.pageVolume ?: 100)
        val res = mathContentsReadUseCase.readDetailByMemberId(memberId, ContentsSvcPosbSttsType.Release, pageReq)

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
            memberProfileReadUseCase.readByProfileId(profileId) ?: throw BusinessValidException(NOT_EXIST_MEMBER)
        val profileRes = memberMapper.toProfileResponse(profile)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum ?: 0, req.pageVolume ?: 100)
        val res =
            mathContentsReadUseCase.readDetailByMemberId(
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
        val unitInfoList = mathUnitInfoReadUseCase.readAll()
        val unitIdList: List<Int> = getUnitIdList(unitInfoList, req.searchType, req.unitId)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum ?: 0, req.pageVolume ?: 100)
        val res = mathContentsReadUseCase.readDetailByUnitId(memberId, unitIdList, pageReq)

        return ResponseUtil.ok(mapOf("contents" to res))
    }

    // 내 저장소 문제 조회
    @GetMapping("/repo")
    fun readMyRepoContents(
        @UserId memberId: UUID,
        @ModelAttribute @Valid req: ValidPageRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 저장소에 등록된 문제 id 목록 조회
        val contentsIdList = mathContentsRepoReadUseCase.readContentsIdByMemberId(memberId)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum ?: 0, req.pageVolume ?: 100)
        val res = mathContentsReadUseCase.readById(contentsIdList, pageReq)
        return ResponseUtil.ok(mapOf("contents" to res))
    }

    // 문제 삭제
    @DeleteMapping("/{contentsId}")
    fun deleteContents(
        @UserId memberId: UUID,
        @PathVariable contentsId: Long
    ): ResponseEntity<ResponseData<Any>> {
        // 문제 삭제
        val res = mathContentsModifyUseCase.delete(contentsId, memberId)
        return ResponseUtil.ok(mapOf("contents" to res))
    }

}
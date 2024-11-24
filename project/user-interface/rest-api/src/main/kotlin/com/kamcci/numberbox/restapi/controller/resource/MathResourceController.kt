package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadUseCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceWriteUseCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadUseCase
import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceCreateRequest
import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceUpdateRequest
import com.kamcci.numberbox.restapi.dto.response.common.PageResponseImpl.Companion.paginate
import com.kamcci.numberbox.restapi.util.file.FileUtil.toFile
import com.kamcci.numberbox.restapi.util.file.FileUtil.toPptSlide
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RequestMapping("/math/resource")
@RestController
class MathResourceController(
    private val fileUseCase: FileUseCase,
    private val mathResourceMenuReadUseCase: MathResourceMenuReadUseCase,
    private val mathResourceReadUseCase: MathResourceReadUseCase,
    private val mathResourceWriteUseCase: MathResourceWriteUseCase,
) {
    /**
     * 조회 - 카테고리 id로
     */
    @GetMapping("/{mainCateId}")
    fun read(
        @PathVariable
        mainCateId: Int,
        @ModelAttribute
        pageReq: PageRequestImpl
    ): ResponseEntity<ResponseData<Any>> {
        val contents = mathResourceReadUseCase.readByMainCateId(mainCateId, pageReq)
        val rs = paginate(contents, pageReq) { mathResourceReadUseCase.countByMainCateId(mainCateId) }
        return ResponseUtil.ok(rs)
    }

    /**
     * 조회 - 나의 학습 자료
     */
    @GetMapping("/my")
    fun read(
        @UserId
        memberId: UUID,
        @ModelAttribute
        pageReq: PageRequestImpl
    ): ResponseEntity<ResponseData<Any>> {
        // 컨텐츠
        val contents = mathResourceReadUseCase.readByMemberId(memberId, pageReq)
        val rs = paginate(contents, pageReq) { mathResourceReadUseCase.countByMemberId(memberId) }

        // 카테고리 메뉴
        val menuList = mathResourceMenuReadUseCase.readAll()

        return ResponseUtil.ok(
            mapOf(
                "resource" to rs,
                "menu" to menuList
            )
        )
    }

    /**
     * 등록
     */
    @PostMapping
    fun save(
        @UserId
        memberId: UUID,
        @ModelAttribute @Valid
        request: MathResourceCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 1. ppt 파일 업로드
        val pptFileNameVo = fileUseCase.upload(toFile(request.pptFile), FileType.PptResource)

        // 2. ppt 슬라이드 이미지 업로드
        val slideImgNameList: MutableList<FileNameVo> = mutableListOf()
        for (slideImg in toPptSlide(request.pptFile)) {
            val imgFileNameVo = fileUseCase.upload(slideImg, FileType.PptImage)
            slideImgNameList.add(imgFileNameVo)
        }

        // 3. 대표 이미지 존재시 업로드
        val imgFileNameVo = if (request.imgFile != null) {
            val imgFileNameVo = fileUseCase.upload(toFile(request.imgFile), FileType.PptImage)
            imgFileNameVo
        } else null

        // 4. 영속화 목적 dto 생성(대표 이미지 미존재시 슬라이드 첫번째 이미지로 설정)
        val createDto = MathResourceCreateDto(
            memberId = memberId,
            title = request.title,
            pptFilePath = pptFileNameVo.path,
            pptFileName = pptFileNameVo.name,
            pptPageCnt = slideImgNameList.size,
            imgPath = imgFileNameVo?.path ?: slideImgNameList[0].path,
            imgName = imgFileNameVo?.name ?: slideImgNameList[0].name,
            cateList = request.cateList,
            imgList = slideImgNameList
        )
        // 학습 자료 영속화
        val resourceId = mathResourceWriteUseCase.create(createDto)
        return ResponseUtil.ok(resourceId)
    }

    /**
     * 수정
     */
    @PutMapping
    fun update(
        @UserId
        memberId: UUID,
        @ModelAttribute @Valid
        request: MathResourceUpdateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 1. ppt 파일 수정시 업로드
        val pptFileNameVo = if (request.pptFile != null) {
            fileUseCase.upload(toFile(request.pptFile), FileType.PptResource)
        } else null

        // 2. ppt 슬라이드 수정시 업로드
        val slideImgNameList: MutableList<FileNameVo> = mutableListOf()
        if (request.pptFile != null) {
            for (inpStream in toPptSlide(request.pptFile)) {
                val imgFileNameVo = fileUseCase.upload(inpStream, FileType.PptImage)
                slideImgNameList.add(imgFileNameVo)
            }
        }

        // 3. 대표 이미지 수정시 업로드
        val imgFileNameVo = if (request.imgFile != null) {
            fileUseCase.upload(toFile(request.imgFile), FileType.PptImage)
        } else null

        // 4. 영속화 목적 dto 생성(대표 이미지 미존재시 슬라이드 첫번째 이미지로 설정)
        val updateDto = MathResourceUpdateDto(
            resourceId = request.resourceId,
            title = request.title,
            pptFilePath = pptFileNameVo?.path,
            pptFileName = pptFileNameVo?.name,
            pptPageCnt = if (slideImgNameList.isEmpty()) null else slideImgNameList.size,
            imgPath = imgFileNameVo?.path,
            imgName = imgFileNameVo?.name,
            cateList = request.cateList,
            imgList = slideImgNameList
        )

        // 학습 자료 수정
        mathResourceWriteUseCase.update(updateDto)

        // 수정된 학습 자료 반환
        val updatedVo = mathResourceReadUseCase.readById(updateDto.resourceId)
        return ResponseUtil.ok(updatedVo)
    }

    /**
     * 삭제
     */
    @DeleteMapping("{resourceId}")
    fun delete(
        @UserId
        memberId: UUID,
        @PathVariable
        resourceId: Long
    ): ResponseEntity<ResponseData<String>> {
        mathResourceWriteUseCase.deleteByIdAndMemberId(resourceId, memberId)
        return ResponseUtil.ok()
    }
}
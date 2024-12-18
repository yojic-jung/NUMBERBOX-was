package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceWriteCase
import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceCreateRequest
import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceUpdateRequest
import com.kamcci.numberbox.restapi.util.file.FileUtil.toFile
import com.kamcci.numberbox.restapi.util.file.FileUtil.toPptSlide
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*


@RequestMapping("/math/resource")
@RestController
class MathResourceWriteController(
    private val fileUseCase: FileUseCase,
    private val mathResourceReadCase: MathResourceReadCase,
    private val mathResourceWriteCase: MathResourceWriteCase,
) {
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
        // 1. ppt 업로드
        val pptVo = uploadPPT(request.pptFile)

        // 2. 대표 이미지 업로드
        val imgFileNameVo = uploadImg(request.imgFile)

        // 3. 영속화 목적 dto 생성(대표 이미지 미존재시 슬라이드 첫번째 이미지로 설정)
        val pptFileNameVo = pptVo.first
        val slideImgNameList = pptVo.second
        val createDto = MathResourceCreateDto(
            memberId = memberId,
            title = request.title,
            pptFilePath = pptFileNameVo!!.path,
            pptFileName = pptFileNameVo.name,
            pptPageCnt = slideImgNameList.size,
            imgPath = if (imgFileNameVo != null) imgFileNameVo.path else slideImgNameList[0].path,
            imgName = if (imgFileNameVo != null) imgFileNameVo.name else slideImgNameList[0].name,
            cateList = request.cateList,
            imgList = pptVo.second
        )
        // 학습 자료 영속화
        val resourceId = mathResourceWriteCase.create(createDto)
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
        // 1. ppt 업로드
        val pptVo = uploadPPT(request.pptFile)

        // 2. 대표 이미지 수정시 업로드
        val imgFileNameVo = uploadImg(request.imgFile)

        // 3. 영속화 목적 dto 생성(대표 이미지 미존재시 슬라이드 첫번째 이미지로 설정)
        val pptFileNameVo = pptVo.first
        val slideImgNameList = pptVo.second
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
        mathResourceWriteCase.update(updateDto)

        // 수정된 학습 자료 반환
        val updatedVo = mathResourceReadCase.readById(updateDto.resourceId)
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
        mathResourceWriteCase.deleteByIdAndMemberId(resourceId, memberId)
        return ResponseUtil.ok()
    }

    // ppt 업로드
    private fun uploadPPT(pptFile: MultipartFile?): Pair<FileNameVo?, List<FileNameVo>> {
        // 1. ppt 파일 존재시 업로드(수정시에는 미존재일 수 있음)
        val pptFileNameVo = if (pptFile != null) {
            fileUseCase.upload(toFile(pptFile), FileType.PptResource)
        } else null

        // 2. ppt 슬라이드 업로드
        val slideImgNameList: MutableList<FileNameVo> = mutableListOf()
        if (pptFile != null) {
            for (inpStream in toPptSlide(pptFile)) {
                val imgFileNameVo = fileUseCase.upload(inpStream, FileType.PptImage)
                slideImgNameList.add(imgFileNameVo)
            }
        }
        return Pair(pptFileNameVo, slideImgNameList)
    }

    // 이미지 업로드
    private fun uploadImg(imgFile: MultipartFile?) =
        if (imgFile != null) {
            fileUseCase.upload(toFile(imgFile), FileType.PptImage)
        } else null
}
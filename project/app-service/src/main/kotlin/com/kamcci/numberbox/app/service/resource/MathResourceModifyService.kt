package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateOrmDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdtOrmDto
import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteCreateDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType.PptImage
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType.PptResource
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceModifyOrmPort
import com.kamcci.numberbox.app.port.orm.resource.MathResourceReadOrmPort
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileModifyOrmPort
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceModifyUseCase
import java.util.*

@UseCase
class MathResourceModifyService(
    private val fileUseCase: FileUseCase,
    private val mathResourceReadOrmPort: MathResourceReadOrmPort,
    private val mathResourceModifyOrmPort: MathResourceModifyOrmPort,
    private val sysGarbageFileModifyOrmPort: SysGarbageFileModifyOrmPort,
) : MathResourceModifyUseCase {
    companion object {
        const val NOT_MY_CONTENTS = "존재하지 않거나 자신의 컨텐츠가 아닙니다."
    }

    @TXExecute
    override fun create(createDto: MathResourceCreateDto): Long {
        // 1. ppt 파일 업로드
        val pptFileNameVo = fileUseCase.upload(createDto.pptFile, PptResource)

        // 2. ppt 슬라이드 이미지 업로드
        val slideImgNameList: MutableList<FileNameVo> = mutableListOf()
        for (slideImg in createDto.slideImgList) {
            val imgFileNameVo = fileUseCase.upload(slideImg, PptImage)
            slideImgNameList.add(imgFileNameVo)
        }

        // 3. 대표 이미지 존재시 업로드
        val imgFileNameVo = if (createDto.imgFile != null) {
            val imgFileNameVo = fileUseCase.upload(createDto.imgFile!!, PptImage)
            imgFileNameVo
        } else null

        // 4. 영속화 목적 dto 생성(대표 이미지 미존재시 슬라이드 첫번째 이미지로 설정)
        val resourceSaveDto = MathResourceCreateOrmDto(
            memberId = createDto.memberId,
            title = createDto.title,
            pptFilePath = pptFileNameVo.path,
            pptFileName = pptFileNameVo.name,
            pptPageCnt = slideImgNameList.size,
            imgPath = imgFileNameVo?.path ?: slideImgNameList[0].path,
            imgName = imgFileNameVo?.name ?: slideImgNameList[0].name,
            cateList = createDto.cateList,
            imgList = slideImgNameList
        )

        // 5. 학습자료 영속화
        val resourceId = mathResourceModifyOrmPort.create(resourceSaveDto)
        return resourceId
    }

    @TXExecute
    override fun update(updateDto: MathResourceUpdateDto) {
        // 0. 이전 파일 조회
        val prevFile = mathResourceReadOrmPort.readFileById(updateDto.resourceId)
        val deleteImgList: MutableList<FileDeleteCreateDto> = mutableListOf()

        // 1. ppt 파일 업로드
        val pptFileNameVo = if (updateDto.pptFile != null) {
            // 이전 파일 삭제 대상에 추가
            deletePrevFile(prevFile.pptPath, prevFile.pptName, deleteImgList)

            // 신규 파일 업로드
            fileUseCase.upload(updateDto.pptFile!!, PptResource)
        } else null

        // 2. ppt 슬라이드 이미지 업로드
        val slideImgNameList: MutableList<FileNameVo> = mutableListOf()
        if (updateDto.slideImgList.isNotEmpty()) {
            prevFile.imgList.forEach {
                // 이전 파일 삭제 대상에 추가
                deletePrevFile(it.imgPath, it.imgName, deleteImgList)
            }

            for (inpStream in updateDto.slideImgList) {
                // 신규 파일 업로드
                val imgFileNameVo = fileUseCase.upload(inpStream, PptImage)
                slideImgNameList.add(imgFileNameVo)
            }
        }

        // 3. 대표 이미지 존재시 업로드
        val imgFileNameVo = if (updateDto.imgFile != null) {
            // 이전 파일 삭제 대상에 추가
            deletePrevFile(prevFile.imgPath, prevFile.imgName, deleteImgList)

            // 신규 파일 업로드
            fileUseCase.upload(updateDto.imgFile!!, PptImage)
        } else null

        // 4. 영속화 목적 dto 생성(대표 이미지 미존재시 슬라이드 첫번째 이미지로 설정)
        val updateOrmDto = MathResourceUpdtOrmDto(
            resourceId = updateDto.resourceId,
            title = updateDto.title,
            pptFilePath = pptFileNameVo?.path,
            pptFileName = pptFileNameVo?.name,
            pptPageCnt = if (slideImgNameList.isEmpty()) null else slideImgNameList.size,
            imgPath = imgFileNameVo?.path,
            imgName = imgFileNameVo?.name,
            cateList = updateDto.cateList,
            imgList = slideImgNameList
        )

        // 5. 학습자료 수정
        mathResourceModifyOrmPort.update(updateOrmDto)

        // 이전 이미지 삭제
        deleteImgList.forEach {
            sysGarbageFileModifyOrmPort.create(FileDeleteCreateDto(GarbageFileType.S3, it.path, it.name))
        }
    }

    private fun deletePrevFile(
        prevImgPath: String,
        prevImgName: String,
        deleteImgList: MutableList<FileDeleteCreateDto>
    ) {
        val prevImg = FileDeleteCreateDto(GarbageFileType.S3, prevImgPath, prevImgName)
        deleteImgList.add(prevImg)
    }

    @TXExecute
    override fun deleteByIdAndMemberId(id: Long, memberId: UUID) {
        mathResourceModifyOrmPort.deleteByIdAndMemberId(id, memberId).let {
            if (it != 1L) throw BusinessValidException(NOT_MY_CONTENTS)
        }
    }
}
package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateOrmDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdtOrmDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResFileModifyStatusVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceCateModifyOrmPort
import com.kamcci.numberbox.app.port.orm.resource.MathResourceImgModifyOrmPort
import com.kamcci.numberbox.app.port.orm.resource.MathResourceModifyOrmPort
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.common.file.FileNameMaker
import com.kamcci.numberbox.app.usecase.resource.MathResourceModifyUseCase

@UseCase
class MathResourceModifyService(
    private val fileNameMaker: FileNameMaker,
    private val fileStoragePort: FileStoragePort,
    private val mathResourceModifyOrmPort: MathResourceModifyOrmPort,
    private val mathResourceCateModifyOrmPort: MathResourceCateModifyOrmPort,
    private val mathResourceImgModifyOrmPort: MathResourceImgModifyOrmPort,
) : MathResourceModifyUseCase {
    @TXExecute
    override fun create(createDto: MathResourceCreateDto): Long {
        // 1. ppt 파일 업로드
        val pptFileNameVo = fileNameMaker.makeFileNameByType(createDto.pptFileOriginalName, FileType.PptResource)
        fileStoragePort.upload(pptFileNameVo.path, pptFileNameVo.name, createDto.pptFile)

        // 2. ppt 슬라이드 이미지 업로드
        val slideImgNameList: MutableList<FileNameVo> = mutableListOf()
        for (inpStream in createDto.slideImgList) {
            val imgFileNameVo = fileNameMaker.makeFileNameByType("tmpImgName.png", FileType.PptImage)
            fileStoragePort.upload(imgFileNameVo.path, imgFileNameVo.name, inpStream)
            slideImgNameList.add(imgFileNameVo)
        }

        // 3. 대표 이미지 존재시 업로드
        val imgFileNameVo = if (!createDto.imgFileOriginalName.isNullOrEmpty()) {
            val imgFileNameVo = fileNameMaker.makeFileNameByType(createDto.imgFileOriginalName!!, FileType.PptImage)
            fileStoragePort.upload(imgFileNameVo.path, imgFileNameVo.name, createDto.imgFile!!)
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
        )

        // 5. 학습자료 영속화
        val resourceId = mathResourceModifyOrmPort.create(resourceSaveDto)
        mathResourceCateModifyOrmPort.create(resourceId, createDto.cateList)
        mathResourceImgModifyOrmPort.create(resourceId, slideImgNameList)
        return resourceId
    }

    @TXExecute
    override fun update(updateDto: MathResourceUpdateDto): MathResFileModifyStatusVo {
        // 1. ppt 파일 업로드
        val (pptFileNameVo, isPptModified) = if (updateDto.pptFile != null) {
            val pptFileNameVo = fileNameMaker.makeFileNameByType(updateDto.pptFileOriginalName!!, FileType.PptResource)
            fileStoragePort.upload(pptFileNameVo.path, pptFileNameVo.name, updateDto.pptFile!!)
            pptFileNameVo to true
        } else null to false


        // 2. ppt 슬라이드 이미지 업로드
        val slideImgNameList: MutableList<FileNameVo> = mutableListOf()
        for (inpStream in updateDto.slideImgList) {
            val imgFileNameVo = fileNameMaker.makeFileNameByType("tmpImgName.png", FileType.PptImage)
            fileStoragePort.upload(imgFileNameVo.path, imgFileNameVo.name, inpStream)
            slideImgNameList.add(imgFileNameVo)
        }

        // 3. 대표 이미지 존재시 업로드
        val (imgFileNameVo, isImgModified) = if (!updateDto.imgFileOriginalName.isNullOrEmpty()) {
            val imgFileNameVo = fileNameMaker.makeFileNameByType(updateDto.imgFileOriginalName!!, FileType.PptImage)
            fileStoragePort.upload(imgFileNameVo.path, imgFileNameVo.name, updateDto.imgFile!!)
            imgFileNameVo to true
        } else null to false

        // 4. 영속화 목적 dto 생성(대표 이미지 미존재시 슬라이드 첫번째 이미지로 설정)
        val updateOrmDto = MathResourceUpdtOrmDto(
            resourceId = updateDto.resourceId,
            title = updateDto.title,
            pptFilePath = pptFileNameVo?.path,
            pptFileName = pptFileNameVo?.name,
            pptPageCnt = if (slideImgNameList.isEmpty()) null else slideImgNameList.size,
            imgPath = imgFileNameVo?.path,
            imgName = imgFileNameVo?.name,
        )

        // 5. 학습자료 수정
        mathResourceModifyOrmPort.update(updateOrmDto)

        // 카테고리 수정
        mathResourceCateModifyOrmPort.deleteByResourceId(updateDto.resourceId)
        mathResourceCateModifyOrmPort.create(updateDto.resourceId, updateDto.cateList)

        // 이미지 수정
        if (slideImgNameList.isNotEmpty()) {
            mathResourceImgModifyOrmPort.deleteByResourceId(updateDto.resourceId)
            mathResourceImgModifyOrmPort.create(updateDto.resourceId, slideImgNameList)
        }
        return MathResFileModifyStatusVo(isPptModified, isImgModified)
    }
}
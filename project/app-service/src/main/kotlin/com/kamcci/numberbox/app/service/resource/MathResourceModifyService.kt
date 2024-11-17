package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateOrmDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdtOrmDto
import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteCreateDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType.PptImage
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType.PptResource
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceCateModifyOrmPort
import com.kamcci.numberbox.app.port.orm.resource.MathResourceImgModifyOrmPort
import com.kamcci.numberbox.app.port.orm.resource.MathResourceModifyOrmPort
import com.kamcci.numberbox.app.port.orm.resource.MathResourceReadOrmPort
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.common.file.FileNameMaker
import com.kamcci.numberbox.app.usecase.resource.MathResourceModifyUseCase
import java.io.InputStream

@UseCase
class MathResourceModifyService(
    private val fileNameMaker: FileNameMaker,
    private val fileStoragePort: FileStoragePort,
    private val mathResourceReadOrmPort: MathResourceReadOrmPort,
    private val mathResourceModifyOrmPort: MathResourceModifyOrmPort,
    private val mathResourceCateModifyOrmPort: MathResourceCateModifyOrmPort,
    private val mathResourceImgModifyOrmPort: MathResourceImgModifyOrmPort,
) : MathResourceModifyUseCase {
    @TXExecute
    override fun create(createDto: MathResourceCreateDto): Long {
        // 1. ppt 파일 업로드
        val pptFileNameVo = fileNameMaker.makeFileNameByType(createDto.pptFileOriginalName, PptResource)
        fileStoragePort.upload(pptFileNameVo.path, pptFileNameVo.name, createDto.pptFile)

        // 2. ppt 슬라이드 이미지 업로드
        val slideImgNameList: MutableList<FileNameVo> = mutableListOf()
        for (inpStream in createDto.slideImgList) {
            val imgFileNameVo = fileNameMaker.makeFileNameByType("tmpImgName.png", PptImage)
            fileStoragePort.upload(imgFileNameVo.path, imgFileNameVo.name, inpStream)
            slideImgNameList.add(imgFileNameVo)
        }

        // 3. 대표 이미지 존재시 업로드
        val imgFileNameVo = if (!createDto.imgFileOriginalName.isNullOrEmpty()) {
            val imgFileNameVo = fileNameMaker.makeFileNameByType(createDto.imgFileOriginalName!!, PptImage)
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

        // 5. 학습자료 영속화 todo 영속화 한번에 진행
        val resourceId = mathResourceModifyOrmPort.create(resourceSaveDto)
        mathResourceCateModifyOrmPort.create(resourceId, createDto.cateList)
        mathResourceImgModifyOrmPort.create(resourceId, slideImgNameList)
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
            uploadNewFile(updateDto.pptFile!!, updateDto.pptFileOriginalName!!, PptResource)
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
                val imgFileNameVo = uploadNewFile(inpStream, "_.png", PptImage)
                slideImgNameList.add(imgFileNameVo)
            }
        }

        // 3. 대표 이미지 존재시 업로드
        val imgFileNameVo = if (!updateDto.imgFileOriginalName.isNullOrEmpty()) {
            // 이전 파일 삭제 대상에 추가
            deletePrevFile(prevFile.imgPath, prevFile.imgName, deleteImgList)

            // 신규 파일 업로드
            uploadNewFile(updateDto.imgFile!!, updateDto.imgFileOriginalName!!, PptImage)
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
        )

        // 5. 학습자료 수정  todo 영속화 한번에 진행
        mathResourceModifyOrmPort.update(updateOrmDto)

        // 카테고리 수정
        mathResourceCateModifyOrmPort.deleteByResourceId(updateDto.resourceId)
        mathResourceCateModifyOrmPort.create(updateDto.resourceId, updateDto.cateList)

        // 이미지 수정
        if (slideImgNameList.isNotEmpty()) {
            mathResourceImgModifyOrmPort.deleteByResourceId(updateDto.resourceId)
            mathResourceImgModifyOrmPort.create(updateDto.resourceId, slideImgNameList)
        }
    }

    private fun uploadNewFile(
        uploadFile: InputStream,
        uploadFileName: String,
        fileType: FileType,
    ): FileNameVo {
        // 새 파일 업로드
        val fileNameVo = fileNameMaker.makeFileNameByType(uploadFileName, fileType)
        fileStoragePort.upload(fileNameVo.path, fileNameVo.name, uploadFile)
        return fileNameVo
    }

    private fun deletePrevFile(
        prevImgPath: String,
        prevImgName: String,
        deleteImgList: MutableList<FileDeleteCreateDto>
    ) {
        val prevImg = FileDeleteCreateDto(GarbageFileType.S3, prevImgPath, prevImgName)
        deleteImgList.add(prevImg)
    }
}
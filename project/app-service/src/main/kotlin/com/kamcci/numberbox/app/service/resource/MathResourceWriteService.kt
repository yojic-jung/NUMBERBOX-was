package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteCreateDto
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.resource.MathResourceModifyOrmPort
import com.kamcci.numberbox.app.port.orm.resource.MathResourceReadOrmPort
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileModifyOrmPort
import com.kamcci.numberbox.app.usecase.resource.MathResourceWriteUseCase
import java.util.*

@UseCase
class MathResourceWriteService(
    private val mathResourceReadOrmPort: MathResourceReadOrmPort,
    private val mathResourceModifyOrmPort: MathResourceModifyOrmPort,
    private val sysGarbageFileModifyOrmPort: SysGarbageFileModifyOrmPort,
) : MathResourceWriteUseCase {
    companion object {
        const val NOT_MY_CONTENTS = "존재하지 않거나 자신의 컨텐츠가 아닙니다."
    }

    @TXExecute
    override fun create(createDto: MathResourceCreateDto): Long {
        return mathResourceModifyOrmPort.create(createDto)
    }

    @TXExecute
    override fun update(updateDto: MathResourceUpdateDto) {
        // 0. 이전 파일 조회
        val prevFile = mathResourceReadOrmPort.readFileById(updateDto.resourceId)
        val deleteImgList: MutableList<FileDeleteCreateDto> = mutableListOf()

        // 1. ppt 파일 이전 파일 삭제 대상에 추가
        if (updateDto.pptFilePath != null && updateDto.pptFileName != null) {
            deletePrevFile(prevFile.pptPath, prevFile.pptName, deleteImgList)
        }

        // 2. ppt 슬라이드 이미지 존재시 이전 파일 삭제 대상에 추가
        if (updateDto.imgList.isNotEmpty()) {
            prevFile.imgList.forEach {
                //
                deletePrevFile(it.imgPath, it.imgName, deleteImgList)
            }
        }

        // 3. 대표 이미지 이전 파일 삭제 대상에 추가
        if (updateDto.imgPath != null && updateDto.imgName != null) {
            deletePrevFile(prevFile.imgPath, prevFile.imgName, deleteImgList)
        }

        // 5. 학습자료 수정
        mathResourceModifyOrmPort.update(updateDto)

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
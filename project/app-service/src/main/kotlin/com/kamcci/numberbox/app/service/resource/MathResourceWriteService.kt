package com.kamcci.numberbox.app.service.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.resource.MathResourceWriteOrmPort
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceWriteCase
import java.util.*

@UseCase
class MathResourceWriteService(
    private val mathResourceReadOrmPort: MathResourceReadCase,
    private val mathResourceWriteOrmPort: MathResourceWriteOrmPort,
    private val sysGarbageFileWriteOrmPort: SysGarbageFileWriteOrmPort,
) : MathResourceWriteCase {
    companion object {
        const val NOT_MY_CONTENTS = "존재하지 않거나 자신의 컨텐츠가 아닙니다."
    }

    @TXExecute
    override fun create(createDto: MathResourceCreateDto): Long {
        return mathResourceWriteOrmPort.create(createDto)
    }

    @TXExecute
    override fun update(updateDto: MathResourceUpdateDto) {
        // 0. 이전 파일 조회
        val prevFile = mathResourceReadOrmPort.readFileById(updateDto.resourceId)
        val deleteImgList: MutableList<FileDeleteDto> = mutableListOf()

        // 1. ppt 파일 이전 파일 삭제 대상에 추가
        if (updateDto.pptFilePath != null && updateDto.pptFileName != null) {
            deletePrevFile(prevFile.pptPath, prevFile.pptName, deleteImgList)
        }

        // 2. ppt 슬라이드 이미지 존재시 이전 파일 삭제 대상에 추가
        if (updateDto.imgList.isNotEmpty()) {
            prevFile.imgList.forEach {
                deletePrevFile(it.imgPath, it.imgName, deleteImgList)
            }
        }

        // 3. 대표 이미지 이전 파일 삭제 대상에 추가
        if (updateDto.imgPath != null && updateDto.imgName != null) {
            deletePrevFile(prevFile.imgPath, prevFile.imgName, deleteImgList)
        }

        // 5. 학습자료 수정
        mathResourceWriteOrmPort.update(updateDto)

        // 이전 이미지 삭제
        deleteImgList.forEach {
            sysGarbageFileWriteOrmPort.create(FileDeleteDto(GarbageFileType.S3, it.path, it.name))
        }
    }

    private fun deletePrevFile(
        prevImgPath: String,
        prevImgName: String,
        deleteImgList: MutableList<FileDeleteDto>
    ) {
        val prevImg = FileDeleteDto(GarbageFileType.S3, prevImgPath, prevImgName)
        deleteImgList.add(prevImg)
    }

    @TXExecute
    override fun deleteByIdAndMemberId(id: Long, memberId: UUID) {
        mathResourceWriteOrmPort.deleteByIdAndMemberId(id, memberId).let {
            if (it != 1L) throw BusinessInValidException(NOT_MY_CONTENTS)
        }
    }
}
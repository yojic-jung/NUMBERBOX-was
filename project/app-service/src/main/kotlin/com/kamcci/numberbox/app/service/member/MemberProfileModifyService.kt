package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtOrmDto
import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteCreateDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.port.orm.member.MemberProfileModifyOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberProfileReadOrmPort
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileModifyOrmPort
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileModifyUseCase
import java.util.*

@UseCase
class MemberProfileModifyService(
    private val memberProfileReadOrmPort: MemberProfileReadOrmPort,
    private val memberProfileModifyOrmPort: MemberProfileModifyOrmPort,
    private val sysGarbageFileModifyOrmPort: SysGarbageFileModifyOrmPort,
    private val fileUseCase: FileUseCase
) : MemberProfileModifyUseCase {
    @TXExecute
    override fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType): Boolean {
        return memberProfileModifyOrmPort.updateProfileTypeByMemberId(memberId, profileType) > 0
    }

    @TXExecute
    override fun updateImgByMemberId(updateDto: MemberProfileImgUpdtDto): FileNameVo {
        // 1. 이미 등록된 프로필 이미지 정보 가져오기
        val prevImgVo = memberProfileReadOrmPort.readProfileImgByMemberId(updateDto.memberId)

        // 2. 프로필 이미지 파일 스토리지에 저장
        val fileNameVo = fileUseCase.upload(updateDto.imgFile, FileType.ProfileIMG)

        // 3. 프로필 이미지 파일 정보 DB에 저장
        val imgUpdtDto = MemberProfileImgUpdtOrmDto(updateDto.memberId, fileNameVo.path, fileNameVo.name)
        memberProfileModifyOrmPort.updateImgByMemberId(imgUpdtDto)

        // 4. 삭제 대상 이미지 저장(추후 스케줄러를 통해 일괄 삭제됨)
        val filePath = prevImgVo?.profileImgPath
        val fileName = prevImgVo?.profileImgName
        if (!fileName.isNullOrEmpty() && !filePath.isNullOrEmpty()) {
            sysGarbageFileModifyOrmPort.create(FileDeleteCreateDto(GarbageFileType.S3, filePath, fileName))
        }
        return fileNameVo
    }

    @TXExecute
    override fun updateNicknameByMemberId(memberId: UUID, nickname: String): Boolean {
        return memberProfileModifyOrmPort.updateNicknameByMemberId(memberId, nickname) > 0
    }

    @TXExecute
    override fun updateHwpDownCnt(hwpDownCnt: Int): Long {
        return memberProfileModifyOrmPort.updateHwpDownCntByMemberId(hwpDownCnt)
    }
}
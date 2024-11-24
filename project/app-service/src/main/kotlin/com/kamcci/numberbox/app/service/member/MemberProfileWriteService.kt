package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteCreateDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.member.MemberProfileReadOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberProfileWriteOrmPort
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.member.MemberProfileWriteCase
import java.util.*

@UseCase
class MemberProfileWriteService(
    private val memberProfileReadOrmPort: MemberProfileReadOrmPort,
    private val memberProfileWriteOrmPort: MemberProfileWriteOrmPort,
    private val sysGarbageFileWriteOrmPort: SysGarbageFileWriteOrmPort,
    private val fileUseCase: FileUseCase
) : MemberProfileWriteCase {
    @TXExecute
    override fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType) {
        memberProfileWriteOrmPort.updateProfileTypeByMemberId(memberId, profileType)
    }

    @TXExecute
    override fun updateImgByMemberId(updateDto: MemberProfileImgUpdtDto) {
        // 1. 이미 등록된 프로필 이미지 정보 가져오기
        val prevImgVo = memberProfileReadOrmPort.readProfileImgByMemberId(updateDto.memberId)

        // 2. 프로필 이미지 파일 정보 DB에 저장
        memberProfileWriteOrmPort.updateImgByMemberId(updateDto)

        // 3. 삭제 대상 이미지 저장(추후 스케줄러를 통해 일괄 삭제됨)
        val filePath = prevImgVo?.profileImgPath
        val fileName = prevImgVo?.profileImgName
        if (!fileName.isNullOrEmpty() && !filePath.isNullOrEmpty()) {
            sysGarbageFileWriteOrmPort.create(FileDeleteCreateDto(GarbageFileType.S3, filePath, fileName))
        }
    }

    @TXExecute
    override fun updateNicknameByMemberId(memberId: UUID, nickname: String) {
        memberProfileWriteOrmPort.updateNicknameByMemberId(memberId, nickname)
    }

    @TXExecute
    override fun updateHwpDownCnt(hwpDownCnt: Int): Long {
        return memberProfileWriteOrmPort.updateHwpDownCntByMemberId(hwpDownCnt)
    }
}
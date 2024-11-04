package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.port.repository.member.MemberProfileModifyOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberProfileReadOrmPort
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.common.file.FileNameMaker
import com.kamcci.numberbox.app.usecase.member.MemberProfileModifyUseCase
import java.io.InputStream
import java.util.*

@UseCase
class MemberProfileModifyService(
    private val memberProfileReadOrmPort: MemberProfileReadOrmPort,
    private val memberProfileModifyOrmPort: MemberProfileModifyOrmPort,
    private val fileStoragePort: FileStoragePort,
    private val fileNameMaker: FileNameMaker
) : MemberProfileModifyUseCase {
    @TXExecute
    override fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType): Boolean {
        return memberProfileModifyOrmPort.updateProfileTypeByMemberId(memberId, profileType) > 0
    }

    @TXExecute
    override fun updateImgByMemberId(memberId: UUID, fileName: String, inpStream: InputStream): FileNameVo {
        // 1. 이미 등록된 프로필 이미지 정보 가져오기
        val profileImgVo = memberProfileReadOrmPort.readProfileImgByMemberId(memberId)

        // 2. 프로필 이미지 파일 스토리지에 저장
        val fileNameDto = fileNameMaker.makeFileNameByType(fileName, FileType.ProfileIMG)
        fileStoragePort.upload("${fileNameDto.path}/${fileNameDto.name}", inpStream)

        // 3. 프로필 이미지 파일 정보 DB에 저장
        val imgUpdtDto = MemberProfileImgUpdtDto(memberId, fileNameDto.path, fileNameDto.name)
        memberProfileModifyOrmPort.updateImgByMemberId(imgUpdtDto)

        // 4. 이미 존재하는 프로필 이미지 삭제
        val filePath = profileImgVo?.profileImgPath
        val fileName = profileImgVo?.profileImgName
        if (!fileName.isNullOrEmpty() && !filePath.isNullOrEmpty()) {
            // 이미지 삭제
            fileStoragePort.delete("$filePath/$fileName")
        }
        return fileNameDto
    }

    @TXExecute
    override fun updateNicknameByMemberId(memberId: UUID, nickname: String): Boolean {
        return memberProfileModifyOrmPort.updateNicknameByMemberId(memberId, nickname) > 0
    }
}
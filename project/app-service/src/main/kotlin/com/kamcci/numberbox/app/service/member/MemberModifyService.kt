package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdConfirmDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.port.etc.MemberPasswordEncoder
import com.kamcci.numberbox.app.port.orm.member.*
import com.kamcci.numberbox.app.usecase.member.MemberModifyUseCase
import java.util.*

@UseCase
class MemberModifyService(
    private val memberModifyOrmPort: MemberModifyOrmPort,
    private val memberReadOrmPort: MemberReadOrmPort,
    // 비밀번호 인코더
    private val memberPasswordEncoder: MemberPasswordEncoder,
    // 회원가입 영속화 repository
    private val memberSaveRepo: MemberSaveOrmPort,
    private val roleSaveRepo: MemberRoleSaveOrmPort,
    private val roleReadRepo: MemberRoleReadOrmPort,
    private val profileSaveRepo: MemberProfileSaveOrmPort,
    private val privateSaveRepo: MemberPrivateSaveOrmPort,
) : MemberModifyUseCase {
    @TXExecute
    override fun updatePassword(updtDto: MemberPasswdUpdtDto): Boolean {
        // 이전 비밀번호 일치 여부 확인
        val dbPassword = memberReadOrmPort.readPasswordByMemberId(updtDto.memberId) ?: return false
        val isPasswordEqual = memberPasswordEncoder.matches(updtDto.previousPassword, dbPassword)
        if (!isPasswordEqual) return false

        // 비밀번호 변경
        val encodedPassword = memberPasswordEncoder.encode(updtDto.password)
        return memberModifyOrmPort.updatePassword(updtDto.memberId, encodedPassword)
    }

    @TXExecute
    override fun confirmPassword(confirmDto: MemberPasswdConfirmDto): Boolean {
        val encodedPassword = memberReadOrmPort.readPasswordByMemberId(confirmDto.memberId) ?: return false
        return memberPasswordEncoder.matches(confirmDto.password, encodedPassword)
    }

    @TXExecute
    override fun signup(signUpDto: MemberSignUpDto, privateSignUpDto: MemberPrivateSignUpDto?): MemberSignUpResultVo {
        // [validation] 이메일 중복 여부 체크
        val isEmailExists = memberReadOrmPort.existsByEmail(signUpDto.email)
        if (isEmailExists) throw BusinessValidException("이미 존재하는 이메일입니다.")

        // [회원가입 진행]
        // 1. 계정 가입
        val encodedPassword = memberPasswordEncoder.encode(signUpDto.password)
        val id = memberSaveRepo.save(signUpDto.email, encodedPassword)

        // 2. 프로필 설정
        val nickName = makeNickname()
        profileSaveRepo.save(id, nickName)

        // 3. 개인정보 설정(존재시에만 설정) - 개인정보 없이도 가입 가능(추후 본인인증을 통해 등록 가능)
        privateSignUpDto?.let {
            privateSaveRepo.save(id, it)
        }

        // 4. 권한 설정
        roleSaveRepo.saveUserRole(id)
        val roleList = roleReadRepo.readRoleByMemberId(id)
        return MemberSignUpResultVo(id, signUpDto.email, roleList)
    }

    // 10글자 랜덤 알파벳 닉네임 생성
    private fun makeNickname(): String {
        val chars = ('a'..'z')
        return (1..10)
            .map { chars.random() }
            .joinToString("")
    }

    @TXExecute
    override fun drop() {
        // 회원 탈퇴 요청 대상자 조회(관리자, 매니저 제외)

        // 1. 파일 삭제

        // 2. 개인 정보 파기

        // 3. 팔로우 및 팔로잉 삭제

        // 4. 사용자 프로필 탈퇴 회원으로 전환

        // 5. 사용자 제작 문제 삭제 - 변형문제는 바로 삭제
        // contents_classify = Deleted으로 변환

        // 5. 학습 자료 삭제

        // 6. 좋아요 및 저장소 삭제

        // 7. 학습지 생성내역 삭제

        // 8. 최종 탈퇴 처리(human_status=3(탈퇴회원), enabled=false)
    }

    @TXExecute
    override fun updateTmpPassword(id: List<UUID>) {
        // 로그인시 암호화를 거치기 때문에 null로 선언된 사용자들은 로그인 불가(반드시 새롭게 임시 비밀번호 발급 받아야함)
        memberModifyOrmPort.updatePassword(id, null)
    }
}

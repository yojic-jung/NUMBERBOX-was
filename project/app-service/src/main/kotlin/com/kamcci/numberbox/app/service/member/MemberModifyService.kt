package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.*
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.port.etc.MemberPasswordEncoder
import com.kamcci.numberbox.app.port.repository.member.MemberPrivateSaveOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberProfileSaveOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberRoleSaveOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberSaveOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberSignupValidator

@UseCase
class MemberModifyService(
    // 회원가입 유효성 검증
    private val memberSignupValidator: MemberSignupValidator,
    // 비밀번호 인코더
    private val memberPasswordEncoder: MemberPasswordEncoder,
    // 계정 영속화 repository
    private val memberSaveRepo: MemberSaveOrmPort,
    private val roleSaveRepo: MemberRoleSaveOrmPort,
    private val profileSaveRepo: MemberProfileSaveOrmPort,
    private val privateSaveRepo: MemberPrivateSaveOrmPort,
) : MemberModifyUseCase {
    override fun updatePassword(passwordUpdtDto: MemberPasswdUpdtDto): Boolean {
        // 1. 인증코드 확인

        // 2.
        TODO("Not yet implemented")
    }

    override fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean {
        TODO("Not yet implemented")
    }

    @TXExecute
    override fun signup(signUpDto: MemberSignUpDto, privateSignUpDto: MemberPrivateSignUpDto?): MemberSignUpResultVo {
        // [validation 진행]
        memberSignupValidator.validate(signUpDto)?.let { return it }

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
        return MemberSignUpResultVo(true, MemberSignUpResultVo.SignUpResultMSg.SUCCESS_MSG)
    }

    override fun drop(dropDto: MemberDropDto): Boolean {
        // 1. 인증코드 확인
        TODO("Not yet implemented")
    }

    // 10글자 랜덤 알파벳 닉네임 생성
    private fun makeNickname(): String {
        val chars = ('a'..'z')
        return (1..10)
            .map { chars.random() }
            .joinToString("")
    }
}

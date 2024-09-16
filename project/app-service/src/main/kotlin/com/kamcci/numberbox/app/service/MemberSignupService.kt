package com.kamcci.numberbox.app.service

import com.kamcci.numberbox.app.domain.dto.member.*
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpResultVo.SignUpResultMSg.*
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.email.sender.member.EmailVerifyCodeSender
import com.kamcci.numberbox.app.repository.member.*
import com.kamcci.numberbox.app.usecase.member.MemberSignupUseCase
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@UseCase
class MemberSignupService(
    // 이메일 검증
    val emailVerifyCodeSaveDto: EmailVerifyCodeModifyRepository,
    val emailVerifyCodeReadRepository: EmailVerifyCodeReadRepository,
    // 메일 처리기
    val emailVerifyCodeSender: EmailVerifyCodeSender,
    // 회원 조회
    val memberReadRepository: MemberReadRepository,
    // 비밀번호 인코더
    val memberPasswordEncoder: MemberPasswordEncoder,
    // 계정 영속화 repository
    val memberSaveRepo: MemberSaveRepository,
    val roleSaveRepo: MemberRoleSaveRepository,
    val profileSaveRepo: MemberProfileSaveRepository,
    val privateSaveRepo: MemberPrivateSaveRepository,
) : MemberSignupUseCase {
    companion object {
        // 이메일 인증 코드 유효 시간
        private const val EMAIL_CODE_EXPIRE_TIME = 180
    }


    @TXExecute
    override fun createEmailCode(email: String): Boolean {
        // 이메일 검증 코드 uuid 생성
        val code = UUID.randomUUID().toString()
        val emailCodeSaveDto = EmailVerifyCodeSaveDto(email, code)

        // 검증 코드 이메일 발송
        val message = EmailCodeMessageDto(email, code)
        emailVerifyCodeSender.send(message)

        // 검증 코드 저장
        return emailVerifyCodeSaveDto.save(emailCodeSaveDto)
    }

    @TXExecute
    override fun signup(signUpDto: MemberSignUpDto, privateSignUpDto: MemberPrivateSignUpDto?): MemberSignUpResultVo {
        // [validation 진행]
        validate(signUpDto)?.let { return it }

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
        return MemberSignUpResultVo(true, SUCCESS_MSG)
    }

    /**
     * 회원가입 양식 검증
     */
    private fun validate(signUpDto: MemberSignUpDto): MemberSignUpResultVo? {
        val emailVerifyCodeVo = emailVerifyCodeReadRepository.findByEmail(signUpDto.email)
            ?: throw BusinessInValidException("이메일 검증 코드 미존재시 회원가입이 불가합니다.")
        val duration = Duration.between(emailVerifyCodeVo.sysCreateTime, LocalDateTime.now())

        // 1. 이메일 인증 코드 만료여부 체크
        if (duration.seconds > EMAIL_CODE_EXPIRE_TIME) return MemberSignUpResultVo(false, EXPIRED_MSG)

        // 2. 이메일 코드 일치 여부 체크
        if (!signUpDto.emailVerifyCode.equals(emailVerifyCodeVo.verifyCode)) {
            return MemberSignUpResultVo(false, NOT_MATCH_CODE_MSG)
        }

        // 3. 이메일 중복 여부 체크
        val isEmailExists = memberReadRepository.existsByEmail(signUpDto.email)
        if (isEmailExists) return MemberSignUpResultVo(false, EXIST_EMAIL_MSG)

        return null
    }

    // 10글자 랜덤 알파벳 닉네임 생성
    fun makeNickname(): String {
        val chars = ('a'..'z')
        return (1..10)
            .map { chars.random() }
            .joinToString("")
    }
}
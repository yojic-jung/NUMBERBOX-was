package com.numberbox.members.appservice.service;

import com.numberbox.members.appservice.usecase.MembersLoginUseCase;
import com.numberbox.members.dto.enums.SignUpResultType;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.members.repository.MembersRoleRepository;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.members.restapi.dto.response.SignUpResultDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Qualifier("naver")
@Service
public class NaverLoginService implements MembersLoginUseCase {
    private final MembersRepository membersRepository;
    private final MembersRoleRepository membersRoleRepository;

    public NaverLoginService(
            MembersRepository membersRepository,
            MembersRoleRepository membersRoleRepository
    ) {
        this.membersRepository = membersRepository;
        this.membersRoleRepository = membersRoleRepository;
    }

    @Transactional
    @Override
    public SignUpResultDto login(String requestEmail) {
        Members members = membersRepository.findByEmail(requestEmail);
        List<MembersRole> membersRoleList = membersRoleRepository.findByUserUniqId(members.getUserUniqId());

        if (members != null) {
            String email = members.getEmail();
            UUID userUniqId = members.getUserUniqId();

            List<String> roles = new ArrayList<>();
            boolean isDropAccount = false;
            for (MembersRole membersRole : membersRoleList) {
                // 권한 추가
                roles.add(membersRole.getRoleName());
                if (!membersRole.isEnabled()) {
                    // 탈퇴회원인 경우 로그인 불가 처리
                    isDropAccount = true;
                }
            }

            if (isDropAccount) {
                return new SignUpResultDto(SignUpResultType.DROP_ACCOUNT);
            }

            // 로그인 시간 및 휴먼상태 초기화
            membersRepository.initFailCntZeroAndLastLoginDate(userUniqId, LocalDateTime.now());
            membersRepository.initHumanStatus(userUniqId);
            return new SignUpResultDto(SignUpResultType.SUCCESS, email, userUniqId, roles);

        } else {
            // todo 수정 필요
            return new SignUpResultDto(SignUpResultType.DROP_ACCOUNT);
            /*
             * //로그인 API로 회원가입하는 경우 //휴대폰 인증 체크 boolean existsPhone =
             * membersPrivateRepository.existsByPhoneNumber(membersDto.getPhoneNumber());
             * if(existsPhone) { map.put("isSuccess", "existsPhone"); return map; }
             * map.put("isSuccess", "EndService");
             * membersDto.setPassword(bCryptPasswordEncoder.encode(CommonUtil.
             * makeRandomPassword()) ); membersDto.setHumanStatus(0);
             * membersDto.setFailCount(0); members =
             * membersRepository.save(membersDto.toEntity()); MembersProfileDto
             * membersProfileDto = new MembersProfileDto();
             * membersProfileDto.setUserUniqId(members.getUserUniqId()); //닉네임 10글자 임의 소문자
             * 알파벳으로 설정 int leftLimit = 97; // letter 'a' int rightLimit = 122; // letter
             * 'z' int targetStringLength = 10; Random random = new Random(); String
             * generatedString = random.ints(leftLimit, rightLimit + 1)
             * .limit(targetStringLength) .collect(StringBuilder::new,
             * StringBuilder::appendCodePoint, StringBuilder::append) .toString();
             * membersProfileDto.setNickname(generatedString);
             * membersProfileRepository.save(membersProfileDto.toEntity()); MembersRoleDto
             * membersRoleDto = new MembersRoleDto(); UUID userUniqId =
             * members.getUserUniqId(); membersRoleDto.setUserUniqId(userUniqId);
             * membersRoleDto.setEnabled(true); membersRoleDto.setRoleName("USER");
             * MembersRole membersRole =
             * membersRoleRepository.save(membersRoleDto.toEntity());
             * roleList.add(membersRole); if(membersDto.getUserName() != null) {
             * MembersPrivateDto mebersPrivateDto = new MembersPrivateDto();
             * mebersPrivateDto.setUserUniqId(userUniqId);
             * //mebersPrivateDto.setUserName(membersDto.getUserName());
             * //mebersPrivateDto.setPhoneNumber(membersDto.getPhoneNumber());
             * //mebersPrivateDto.setBirth(membersDto.getBirth());
             * membersPrivateRepository.save(mebersPrivateDto.toEntity()); }
             * map.put("isSuccess", "signUpSuccess");
             */
        }
    }

}

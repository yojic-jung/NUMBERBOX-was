package com.numberbox.members.appservice.service;

import com.numberbox.jwt.service.RefreshTokenInfoService;
import com.numberbox.members.appservice.usecase.NaverLoginUseCase;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.members.repository.MembersRoleRepository;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.security.provider.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NaverLoginService implements NaverLoginUseCase {
    private final MembersRepository membersRepository;
    private final MembersRoleRepository membersRoleRepository;
    private final RefreshTokenInfoService refreshTokenService;
    private final JwtUtil jwtUtil;

    public NaverLoginService(
            MembersRepository membersRepository,
            MembersRoleRepository membersRoleRepository,
            RefreshTokenInfoService refreshTokenService,
            JwtUtil jwtUtil
    ) {
        this.membersRepository = membersRepository;
        this.membersRoleRepository = membersRoleRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    @Override
    public Map<String, String> naverLogin(MembersRequest membersRequest, HttpServletRequest request) {
        String expiredToken = jwtUtil.resolveRefreshToken(request);
        // 로그인시 클라이언트단에 refresh토큰이 남아있는 경우 해당 refresh토큰 db에서 삭제(클라이언트단에 로그아웃시 refresh토큰
        // 삭제하여 정상적인 로직시 해당 로직 타는 경우 없지만 refresh토큰 탈취하여 사용하는 경우 만료시킴 )
        if (expiredToken != null && !expiredToken.isEmpty()) {
            refreshTokenService.deleteByToken(expiredToken);
        }

        HashMap<String, String> map = new HashMap<>();
        Members members = membersRepository.findByEmail(membersRequest.getEmail());
        List<MembersRole> membersRoleList = membersRoleRepository.findByUserUniqId(members.getUserUniqId());

        List<String> role = new ArrayList<>();
        if (members != null) {

            boolean isDropAccount = false;
            for (MembersRole membersRole : membersRoleList) {
                // 권한 추가
                role.add(membersRole.getRoleName());
                if (!membersRole.isEnabled()) {
                    // 탈퇴회원인 경우 로그인 불가 처리
                    isDropAccount = true;
                }
            }

            if (isDropAccount) {
                map.put("isSuccess", "dropAccount");
                return map;
            }

            // 로그인 시간 및 휴먼상태 초기화
            membersRepository.initLastLoginDate(members.getUserUniqId(), LocalDateTime.now());
            membersRepository.initHumanStatus(members.getUserUniqId());
            map.put("isSuccess", "loginSuccess");
        } else {
            map.put("isSuccess", "EndService");
            return map;
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

        String accessToken = jwtUtil.createAccessToken(members.getEmail(), members.getUserUniqId(), role);
        String refreshToken = jwtUtil.createRefreshToken(members.getEmail(), members.getUserUniqId());
        refreshTokenService.addRefreshToken(refreshToken, members.getUserUniqId());

        // 매니저 권한 임시 구현
        boolean isManager = false;
        boolean isAdmin = false;
        for (MembersRole memberRole : membersRoleList) {
            if (memberRole.getRoleName().equals("MANAGER")) {
                isManager = true;
            } else if (memberRole.getRoleName().equals("ADMIN")) {
                isAdmin = true;
            }
        }

        if (isAdmin) {
            map.put("role", "ADMIN");
        } else if (!isAdmin && isManager) {
            map.put("role", "MANAGER");
        } else {
            map.put("role", "USER");
        }

        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;

    }

}

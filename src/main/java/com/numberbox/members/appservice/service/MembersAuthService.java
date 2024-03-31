package com.numberbox.members.appservice.service;

import com.numberbox.jwt.service.RefreshTokenInfoService;
import com.numberbox.jwt.util.JwtUtil;
import com.numberbox.members.dto.MembersPrivateDto;
import com.numberbox.members.dto.MembersProfileDto;
import com.numberbox.members.dto.MembersRoleDto;
import com.numberbox.members.entity.EmailIdCode;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.*;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MembersAuthService implements com.numberbox.members.appservice.usecase.MembersAuthUseCase {
    private final MembersRepository membersRepository;
    private final MembersRoleRepository membersRoleRepository;
    private final MembersPrivateRepository membersPrivateRepository;
    private final MembersProfileRepository membersProfileRepository;

    private final RefreshTokenInfoService refreshTokenService;
    private final EmailIdCodeRepository emailIdCodeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public MembersAuthService(
            MembersRepository membersRepository,
            MembersRoleRepository membersRoleRepository,
            MembersPrivateRepository membersPrivateRepository,
            MembersProfileRepository membersProfileRepository,
            RefreshTokenInfoService refreshTokenService,
            EmailIdCodeRepository emailIdCodeRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            JwtUtil jwtUtil
    ) {
        this.membersRepository = membersRepository;
        this.membersRoleRepository = membersRoleRepository;
        this.membersPrivateRepository = membersPrivateRepository;
        this.membersProfileRepository = membersProfileRepository;
        this.refreshTokenService = refreshTokenService;
        this.emailIdCodeRepository = emailIdCodeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    @Override
    public Map<String, String> signUp(HttpServletRequest request, MembersRequest membersRequest) {
        HashMap<String, String> map = new HashMap<>();
        // 이메일 인증코드 확인
        EmailIdCode emailIdCode = emailIdCodeRepository.findByEmail(membersRequest.getEmail());
        Duration duration = Duration.between(emailIdCode.getSysCreateTime(), LocalDateTime.now());
        if (duration.getSeconds() > 180) {
            map.put("isSuccess", "emailIdCodeExpired");
            return map;
        }
        boolean isEmailIdentified = bCryptPasswordEncoder.matches(membersRequest.getEmailIdCode(), emailIdCode.getIdCode());
        if (!isEmailIdentified) {
            map.put("isSuccess", "emailIdCodeMissMatch");
            return map;
        }

        emailIdCodeRepository.deleteByEmail(membersRequest.getEmail());

        boolean existsEmail = membersRepository.existsByEmail(membersRequest.getEmail());
        if (existsEmail) {
            map.put("isSuccess", "existsEmail");
            return map;
        }
        /*
         * boolean existsPhone =
         * membersPrivateRepository.existsByPhoneNumber(membersDto.getPhoneNumber());
         * if(existsPhone) { map.put("isSuccess", "existsPhone"); return map; }
         */

        membersRequest.setPassword(bCryptPasswordEncoder.encode(membersRequest.getPassword()));
        membersRequest.setHumanStatus(0);
        membersRequest.setFailCount(0);
        Members members = membersRepository.save(membersRequest.toEntity());
        MembersProfileDto membersProfileDto = new MembersProfileDto();
        membersProfileDto.setUserUniqId(members.getUserUniqId());
        // 닉네임 10글자 임의 소문자 알파벳으로 설정
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        membersProfileDto.setNickname(generatedString);
        membersProfileRepository.save(membersProfileDto.toEntity());
        MembersRoleDto membersRoleDto = new MembersRoleDto();
        UUID userUniqId = members.getUserUniqId();
        membersRoleDto.setUserUniqId(userUniqId);
        membersRoleDto.setEnabled(true);
        membersRoleDto.setRoleName("USER");
        MembersRole membersRole = membersRoleRepository.save(membersRoleDto.toEntity());

        MembersPrivateDto mebersPrivateDto = new MembersPrivateDto();
        mebersPrivateDto.setUserUniqId(userUniqId);
        mebersPrivateDto.setUserName(membersRequest.getUserName());
        mebersPrivateDto.setPhoneNumber(membersRequest.getPhoneNumber());
        mebersPrivateDto.setBirth(membersRequest.getBirth());
        membersPrivateRepository.save(mebersPrivateDto.toEntity());

        List<MembersRole> list = new ArrayList<>();
        list.add(membersRole);
        String accessToken = jwtUtil.createAccessToken(request, members.getEmail(), members.getUserUniqId(), list);
        String refreshToken = jwtUtil.createRefreshToken(members.getEmail(), members.getUserUniqId());
        refreshTokenService.addRefreshToken(refreshToken, members.getUserUniqId());
        map.put("isSuccess", "success");
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
    }

}

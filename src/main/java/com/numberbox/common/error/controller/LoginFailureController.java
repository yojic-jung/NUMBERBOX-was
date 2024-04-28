package com.numberbox.common.error.controller;

import com.numberbox.members.entity.Members;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.members.repository.MembersRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginFailureController {
    private final MembersRepository membersRepository;
    private final MembersRoleRepository membersRoleRepository;

    public LoginFailureController(MembersRepository membersRepository,
                                  MembersRoleRepository membersRoleRepository) {
        this.membersRepository = membersRepository;
        this.membersRoleRepository = membersRoleRepository;
    }

    @PostMapping("/loginFail")
    public Map<String, Object> loginFailProcess(HttpServletRequest request, HttpServletResponse response) {
        String userEmail = (String) request.getAttribute("username");
        Exception exception = (Exception) request.getAttribute("numberbox.error.exception");
        System.out.println("failure2");
        Members members = membersRepository.findByEmail(userEmail);
        UUID userUniqId = members.getUserUniqId();

        String customErrMsg = "";
        if (exception instanceof BadCredentialsException) {
            if (members.getFailCount() == 4) {
                membersRoleRepository.disableEnabled(userUniqId);
                customErrMsg = "해당 계정이 잠금되었습니다.\n15분 후 다시 시도해주세요.";
            } else {
                customErrMsg = "이메일과 비밀번호를 다시 한번 입력해주시기 바랍니다.\n5회 이상 실패시 15분간 계정이 비활성화 됩니다.";
            }
            membersRepository.initLastFailTime(userUniqId, LocalDateTime.now());
            membersRepository.increaseFailCount(userUniqId, members.getFailCount() + 1);
        } else if (exception instanceof InternalAuthenticationServiceException) {
            // 없는 계정인 경우
            customErrMsg = "이메일과 비밀번호를 다시 한번 입력해주시기 바랍니다.\n5회 이상 실패시 15분간 계정이 비활성화 됩니다.";
        } else if (exception instanceof DisabledException) {
            LocalDateTime lastFailTimeAfter15m = members.getLastFailTime().plusMinutes(15);
            // 15분 지나면 다시 enabled true로 바꾸고 다시 한번 로그인 하도록 구현
            // failCount=0로 변경
            if (lastFailTimeAfter15m.isBefore(LocalDateTime.now())) {
                membersRoleRepository.ableEnabled(userUniqId);
                membersRepository.initLastLoginDate(userUniqId, LocalDateTime.now());
                customErrMsg = "계정 잠금이 풀렸습니다.\n다시 로그인 시도해주세요.";
            } else {
                membersRepository.initLastFailTime(userUniqId, LocalDateTime.now());
                customErrMsg = "해당 계정이 잠금되었습니다.\n15분 후 다시 시도해주세요.";
            }
        } else if (exception instanceof CredentialsExpiredException) {
            customErrMsg = "계정이 만료되었습니다.";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("customErrMsg", customErrMsg);
        return map;
    }
}

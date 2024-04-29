package com.numberbox.common.error.controller;

import com.numberbox.members.entity.Members;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.members.repository.MembersRoleRepository;
import com.numberbox.security.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.rmi.ServerException;
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
    public ResponseEntity<Map<String, Object>> loginFailProcess(HttpServletRequest request, HttpServletResponse response) {
        Exception exception = (Exception) request.getAttribute("auth.error.exception");
        Map<String, Object> resultMap = new HashMap<>();

        // 계정 존재하지 않음
        if(exception instanceof BadAuthRequestException){
            resultMap.put("customErrMsg", "잘못된 형식의 요청입니다.");
            return new ResponseEntity(resultMap, HttpStatusCode.valueOf(200));
        }

        // 계정 존재하지 않음
        if(exception instanceof UserNotFoundException){
            resultMap.put("customErrMsg", "이메일과 비밀번호를 다시 한번 입력해주시기 바랍니다.");
            return new ResponseEntity(resultMap, HttpStatusCode.valueOf(200));
        }

        String userEmail = (String) request.getAttribute("username");
        Members members = membersRepository.findByEmail(userEmail);
        UUID userUniqId = members.getUserUniqId();

        String customErrMsg = "";
        // 비밀번호 불일치
        if (exception instanceof PasswordDisMatchException) {
            if (members.getFailCount() == 4) {
                membersRoleRepository.disableEnabled(userUniqId);
                customErrMsg = "해당 계정이 잠금되었습니다.\n15분 후 다시 시도해주세요.";
            } else {
                customErrMsg = "이메일과 비밀번호를 다시 한번 입력해주시기 바랍니다.\n5회 이상 실패시 15분간 계정이 비활성화 됩니다.";
            }
            membersRepository.initLastFailTime(userUniqId, LocalDateTime.now());
            membersRepository.increaseFailCount(userUniqId, members.getFailCount() + 1);
        }
        // 비활성화된 계정(ex. 비밀번호 5회 이상 틀림)
        else if (exception instanceof DisabledUserException) {
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
        }
        else {
            customErrMsg = "예상치 못한 서버 예외.";
        }

        resultMap.put("customErrMsg", customErrMsg);
        return new ResponseEntity(resultMap, HttpStatusCode.valueOf(200));
    }
}

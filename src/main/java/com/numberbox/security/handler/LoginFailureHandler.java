package com.numberbox.security.handler;

import com.numberbox.members.entity.Members;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.members.repository.MembersRoleRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private MembersRoleRepository membersRoleRepository;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String userEmail = (String) request.getParameter("username");
        String customErrMsg = "";
        if (exception instanceof BadCredentialsException) {
            Members members = membersRepository.findByEmail(userEmail);
            UUID userUniqId = members.getUserUniqId();
            if (members.getFailCount() == 4) {
                membersRoleRepository.disableEnabled(userUniqId);
                membersRepository.increaseFailCount(userUniqId, members.getFailCount() + 1);
                membersRepository.initLastFailTime(userUniqId, LocalDateTime.now());
                customErrMsg = "해당 계정이 잠금되었습니다.\n15분 후 다시 시도해주세요.";
            } else {
                membersRepository.initLastFailTime(userUniqId, LocalDateTime.now());
                membersRepository.increaseFailCount(userUniqId, members.getFailCount() + 1);
                customErrMsg = "이메일과 비밀번호를 다시 한번 입력해주시기 바랍니다.\n5회 이상 실패시 15분간 계정이 비활성화 됩니다.";
            }
        } else if (exception instanceof InternalAuthenticationServiceException) {
            // 없는 계정인 경우
            customErrMsg = "이메일과 비밀번호를 다시 한번 입력해주시기 바랍니다.\n5회 이상 실패시 15분간 계정이 비활성화 됩니다.";
        } else if (exception instanceof DisabledException) {
            Members members = membersRepository.findByEmail(userEmail);
            UUID userUniqId = members.getUserUniqId();
            LocalDateTime lastFailTime = members.getLastFailTime();
            lastFailTime = lastFailTime.plusMinutes(15);
            // 15분 지나면 다시 enabled true로 바꾸고 다시 한번 로그인 하도록 구현
            // failCount=0로 변경
            if (lastFailTime.isBefore(LocalDateTime.now())) {
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
        request.setAttribute("customErrMsg", customErrMsg);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginFail");
        dispatcher.forward(request, response);
    }
}

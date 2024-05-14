package com.numberbox.members.appservice.usecase;

import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.members.restapi.dto.response.SignUpResultDto;
import jakarta.servlet.http.HttpServletRequest;

public interface MembersLoginUseCase {
    SignUpResultDto login(String requestEmail);
}

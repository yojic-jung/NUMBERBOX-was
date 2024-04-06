package com.numberbox.members.appservice.usecase;

import com.numberbox.members.restapi.dto.request.MembersRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface NaverLoginUseCase {
    public Map<String, String> naverLogin(MembersRequest membersRequest, HttpServletRequest request);
}

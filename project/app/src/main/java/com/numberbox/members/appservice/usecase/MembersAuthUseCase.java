package com.numberbox.members.appservice.usecase;

import com.numberbox.members.restapi.dto.request.MembersRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface MembersAuthUseCase {
    public Map<String, String> signUp(HttpServletRequest request, MembersRequest membersRequest);
}

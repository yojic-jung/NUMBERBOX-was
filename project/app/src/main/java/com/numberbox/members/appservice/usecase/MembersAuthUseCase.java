package com.numberbox.members.appservice.usecase;

import com.numberbox.members.restapi.dto.request.MembersRequest;

import java.util.Map;

public interface MembersAuthUseCase {
    Map<String, Object> signUp(MembersRequest membersRequest);
}

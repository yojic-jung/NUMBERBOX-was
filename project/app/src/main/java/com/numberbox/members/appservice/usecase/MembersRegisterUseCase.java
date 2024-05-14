package com.numberbox.members.appservice.usecase;

import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.members.restapi.dto.response.SignUpResultDto;

public interface MembersRegisterUseCase {
    SignUpResultDto signUp(MembersRequest membersRequest);
}

package com.kamcci.numberbox.members.appservice.usecase;

import com.kamcci.numberbox.members.restapi.dto.request.MembersRequest;
import com.kamcci.numberbox.members.restapi.dto.response.SignUpResultDto;

public interface MembersRegisterUseCase {
    SignUpResultDto signUp(MembersRequest membersRequest);
}

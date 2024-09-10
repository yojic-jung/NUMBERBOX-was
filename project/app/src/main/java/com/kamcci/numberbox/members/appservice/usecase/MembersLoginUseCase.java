package com.kamcci.numberbox.members.appservice.usecase;

import com.kamcci.numberbox.members.restapi.dto.response.SignUpResultDto;

public interface MembersLoginUseCase {
    SignUpResultDto login(String requestEmail);
}

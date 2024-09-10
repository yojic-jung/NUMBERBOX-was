package com.kamcci.numberbox.members.restapi.dto.response;

import com.kamcci.numberbox.members.dto.enums.SignUpResultType;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class SignUpResultDto {
    private SignUpResultType isSuccess;
    private String email;
    private UUID userUniqId;
    private List<String> roles;

    public SignUpResultDto(SignUpResultType isSuccess) {
        this.isSuccess = isSuccess;
    }

    public SignUpResultDto(SignUpResultType isSuccess, String email, UUID userUniqId, List<String> roles) {
        this.isSuccess = isSuccess;
        this.email = email;
        this.userUniqId = userUniqId;
        this.roles = roles;
    }
}

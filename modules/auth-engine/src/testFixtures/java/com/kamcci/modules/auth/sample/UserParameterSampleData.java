package com.kamcci.modules.auth.sample;

import com.kamcci.modules.auth.control.annotation.UserEmail;
import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.control.annotation.UserRole;

import java.util.List;
import java.util.UUID;

// UserDetailArgumentResolver 클래스의 어노테이션 주입 테스트 데이터
public class UserParameterSampleData {
    public void notSupportAnnot(UUID userId) {

    }

    public void supportAllAnnot(@UserId UUID userId, @UserEmail String email, @UserRole List<String> role) {

    }

}

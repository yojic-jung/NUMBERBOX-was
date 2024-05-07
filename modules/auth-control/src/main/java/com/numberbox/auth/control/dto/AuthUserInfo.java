package com.numberbox.auth.control.dto;

import java.util.List;
import java.util.UUID;

public record AuthUserInfo(String username, UUID userId, String password, List<AuthUserRole> roles) {
}
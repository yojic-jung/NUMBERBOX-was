package com.numberbox.auth.control.dto;

import java.util.List;
import java.util.UUID;

public record Client(UUID userId, String email, List<String> roles) {
}

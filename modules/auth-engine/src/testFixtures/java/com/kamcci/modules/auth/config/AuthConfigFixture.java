package com.kamcci.modules.auth.config;

import com.kamcci.modules.auth.engine.config.AuthJwtProperty;
import com.kamcci.modules.auth.engine.config.AuthLoginUrlProperty;

import java.util.ArrayList;

public class AuthConfigFixture {
    private AuthConfigFixture() { }

    public static AuthJwtProperty getAuthJwtProperty() {
        return new AuthJwtProperty("localSecretKey", "email", "id", "domain.com", "issuer", "audience",
                new AuthJwtProperty.AccessToken("subject", 100000000000000000L), new AuthJwtProperty.RefreshToken(
                        "subject", 100000000000000000L, 100000000000000000L));
    }

    public static AuthLoginUrlProperty getAuthLoginUrlProperty() {
        return new AuthLoginUrlProperty("process", "fail", "logout", new ArrayList<>());
    }
}
package com.kamcci.modules.auth.mock.filter;

import com.kamcci.modules.auth.engine.filter.JwtRequestAuthFilter;
import com.kamcci.modules.auth.mock.common.MockAuthenticationManager;
import com.kamcci.modules.auth.mock.service.MockTokenResponseService;

public class MockJwtRequestAuthFilter extends JwtRequestAuthFilter {
    public MockJwtRequestAuthFilter() {
        super(new MockAuthenticationManager(), new MockTokenResponseService());
    }
}

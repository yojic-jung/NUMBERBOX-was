package com.kamcci.modules.auth.stub.filter;

import com.kamcci.modules.auth.engine.filter.JwtRequestAuthFilter;
import com.kamcci.modules.auth.stub.common.MockAuthenticationManager;
import com.kamcci.modules.auth.stub.service.MockTokenResponseService;

public class MockJwtRequestAuthFilter extends JwtRequestAuthFilter {
    public MockJwtRequestAuthFilter() {
        super(new MockAuthenticationManager(), new MockTokenResponseService());
    }
}

package com.kamcci.modules.auth.engine.handler;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.kamcci.modules.auth.control.dto.AuthResponse.ACCESS_DENIED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CustomAuthenticationEntryPointTest {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint = new CustomAuthenticationEntryPoint();

    @Test
    void commence() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        customAuthenticationEntryPoint.commence(request, response, mock());

        // then
        assertThat(response.getStatus()).isEqualTo(ACCESS_DENIED.statusCode);
    }
}
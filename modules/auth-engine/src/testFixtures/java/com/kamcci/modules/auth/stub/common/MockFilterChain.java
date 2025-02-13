package com.kamcci.modules.auth.stub.common;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

public class MockFilterChain implements FilterChain {
    @Override
    public void doFilter(ServletRequest var1, ServletResponse var2) throws IOException, ServletException {

    }
}

package com.kamcci.modules.auth.engine.filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.kamcci.modules.auth.engine.dto.AuthRequest;
import com.kamcci.modules.auth.engine.exception.AuthInternalException;
import com.kamcci.modules.auth.engine.exception.BadInputRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

/**
 * Def. 로그인 요청 인증 필터
 * - 로그인 요청시에만 수행됨
 * - 인증 매니저에 인증 요청 후 성공시에만 SecurityContext에 Authentication(인증) 객체를 담음
 * - 성공 및 실패 핸들러에게 후처리 요청
 */
public class LoginRequestAuthFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final AuthenticationManager authenticationManager;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public LoginRequestAuthFilter(String processUrl, AuthenticationManager authenticationManager,
                                  AuthenticationSuccessHandler authenticationSuccessHandler,
                                  AuthenticationFailureHandler authenticationFailureHandler) {
        super(new AntPathRequestMatcher(processUrl, HttpMethod.POST.name()));
        super.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {
        try {
            // 사용자 요청 authentication(인증) 객체 추출
            final Authentication authRequest = obtainAuthenticationRequest(request);

            // manager에게 인증 요청(Authentication(인증) 객체 반환하면 SecurityContext에 저장됨)
            return authenticationManager.authenticate(authRequest);
        } catch(AuthenticationException ex) {
            throw ex;
        } catch(MismatchedInputException | JsonParseException ex) {
            // 클라이언트에서 usename, password 올바른 형식으로 요청하지 않음
            logger.warn("시큐리티 인증 요청 형식 올바르지 않음 : " + ex);
            // failureHandler를 태우기 위해 AuthenticationException 타입으로 전환
            throw new BadInputRequestException();
        } catch(Exception ex) {
            logger.error("시큐리티 인증 과정 중 예외 발생 : " + ex);
            // 실패 핸들러 타도록 Auth 예외로 전환
            throw new AuthInternalException();
        }
    }

    /**
     * requestBody로 부터 Authentication(인증 정보) 추출
     */
    private Authentication obtainAuthenticationRequest(HttpServletRequest request) throws IOException {
        final AuthRequest authRequest = objectMapper.readValue(request.getInputStream(), AuthRequest.class);
        final String username = authRequest.username();
        final String password = authRequest.password();

        // json요청에 username과 password 속성 명시하지 않은 경우(속성 명시되어 있으면 빈값 넘어옴)
        if(username == null || password == null) {
            throw new BadInputRequestException();
        }

        // request에 username 저장(예외 처리에서 사용할 수 있도록)
        request.setAttribute("username", username);

        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
        if(authResult != null) request.setAttribute("userId", authResult.getDetails());
    }

    // AuthenticationException 타입 예외 발생시에만 실행됨
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}

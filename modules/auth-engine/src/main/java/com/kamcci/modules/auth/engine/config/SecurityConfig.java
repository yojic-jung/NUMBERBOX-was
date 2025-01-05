package com.kamcci.modules.auth.engine.config;

import com.kamcci.modules.auth.engine.filter.JwtRequestAuthFilter;
import com.kamcci.modules.auth.engine.filter.LoginRequestAuthFilter;
import com.kamcci.modules.auth.engine.handler.JwtLogoutSuccessHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.kamcci.modules.auth.control.config.AuthConstantConfig.*;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(value = {AuthLoginUrlProperty.class})
public class SecurityConfig {
    private final AuthLoginUrlProperty authLoginUrlProperty;
    private final ApplicationEventPublisher eventPublisher;

    public SecurityConfig(AuthLoginUrlProperty authLoginUrlProperty, ApplicationEventPublisher eventPublisher) {
        this.authLoginUrlProperty = authLoginUrlProperty;
        this.eventPublisher = eventPublisher;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/webapp/**");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService,
                                    AuthenticationEntryPoint authenticationEntryPoint,
                                    LoginRequestAuthFilter loginRequestAuthFilter,
                                    JwtRequestAuthFilter jwtRequestAuthFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).httpBasic(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        // 모든 http에 public
                        // 내부 에러 처리 컨트롤러로 전달
                        .requestMatchers(HttpMethod.POST, "/error").permitAll()
                        // 로그인 요청
                        .requestMatchers(HttpMethod.POST, authLoginUrlProperty.process()).permitAll()
                        // 로그인 실패시
                        .requestMatchers(HttpMethod.POST, authLoginUrlProperty.fail()).permitAll()
                        //                        .requestMatchers(HttpMethod.POST, "/accessDenied").permitAll()
                        // 전체 허용 디폴트
                        .requestMatchers("/public/**").permitAll().anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(loginRequestAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtRequestAuthFilter, LoginRequestAuthFilter.class)
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(authenticationEntryPoint));

        // logout
        http.logout(logout -> logout
                // 로그아웃 url
                .logoutRequestMatcher(new AntPathRequestMatcher(authLoginUrlProperty.logout(),
                        HttpMethod.DELETE.name()))
                // 로그아웃 성공 핸들러
                .logoutSuccessHandler(new JwtLogoutSuccessHandler(eventPublisher))
                // 로그아웃 시 쿠키 삭제
                .deleteCookies(REFRESH_TOKEN_NAME));
        return http.build();
    }

    //현재 cors 설정 사실상 의미 없음, web서버와 was 같은 서버에서 동작되고
    //web서버의 로컬에서 경로에 따라 같은 서버의 was로 연결되게끔 설정 (도메인 설정하지 않음)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {    //cors 추가
        CorsConfiguration configuration = new CorsConfiguration();
        // - (3)
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.addExposedHeader(ACCESS_TOKEN_NAME);            // 추가한 코드
        configuration.addExposedHeader(ROLE_NAME);            // 추가한 코드
        //configuration.addExposedHeader("Set-Cookie");			// 추가한 코드
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

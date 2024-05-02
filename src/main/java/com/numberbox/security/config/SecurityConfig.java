package com.numberbox.security.config;

import com.numberbox.security.filter.JwtRequestAuthFilter;
import com.numberbox.security.filter.LoginRequestAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/webapp/**");
    }

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            UserDetailsService userDetailsService,
            AuthenticationEntryPoint authenticationEntryPoint,
            LoginRequestAuthFilter loginRequestAuthFilter,
            JwtRequestAuthFilter jwtRequestAuthFilter
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(HttpMethod.POST, "/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/loginProcess").permitAll()
                        .requestMatchers(HttpMethod.POST, "/loginFail").permitAll()
                        .requestMatchers(HttpMethod.POST, "/accessDenied").permitAll()

                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/naverLogin").permitAll()
                        .requestMatchers(HttpMethod.GET, "/delRefreshToken").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/takeResource").permitAll()
                        .requestMatchers(HttpMethod.GET, "/takeResourceByResourceNo").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/takeMerchantUid").permitAll()
                        .requestMatchers(HttpMethod.GET, "/certifications/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/findEmail").permitAll()
                        .requestMatchers(HttpMethod.GET, "/findPassword").permitAll()
                        .requestMatchers(HttpMethod.GET, "/createEmailIdCode").permitAll()

                        .requestMatchers(HttpMethod.GET, "/myContentsCheckForHwpDown").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/registerMemberProfile").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.GET, "/takeMyEmail").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/confirmPassword").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/changePassword").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/changePhoneNumber").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.GET, "/takeProfile").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/takeUserProfile").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/changeNickname").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/registerProfileImg").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.GET, "/followingUser").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/followingCancel").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "/myAccountDrop").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "/mathInfo/takeWorkContentsList").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/takeWorkContentsListByContentsNo").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/takeContentsListByContentsNo").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/takeContentsList").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/takeMyContentsList").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/takeUserContentsList").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/takeMyRepo").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.GET, "/mathInfo/takeMyWorkContents").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/takeContentsByContentsNo").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.GET, "/mathInfo/myContentsDel").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/myRepoDel").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.GET, "/mathInfo/likeContents").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/putInMyRepo").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "/mathInfo/registerContents").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/registerContentsMulti").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/makeContents").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/registerContentsGrammer").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/conSvcSttsChng").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/delCompContents").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/registerCompContents").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/registerIpsiContents").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/registerResource").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/updateResource").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/takeMyResource").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/myResourceDel").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/takeIpsiYear").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/takeIpsiContentsByYear").hasAnyRole("MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/mathInfo/changeQuesType").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/takeConCntByUnitAndType").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/typeDel").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/mathTypeAdd").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mathInfo/contentsMoveFromTo").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mathInfo/mathTypeOrderChng").hasAnyRole("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/mathDocs/mathDocs").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathDocs/mathDocsIpsi").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathDocs/myMathDocs").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathDocs/delMyMathDocs").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/mathDocs/similarContents").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathDocs/registerMathDocsPaper").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathDocs/registerMathDocsUsage").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/mathDocs/mathDocsByMyMathDocsPage").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.GET, "/serviceCenter/takeErrReport").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/serviceCenter/registerError").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/serviceCenter/takeMyErrReport").hasAnyRole("USER")

                        .requestMatchers(HttpMethod.POST, "/convert/convertHwpToWeb").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.POST, "/convert/changeConverted").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/convert/myHwpConvertContents").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/convert/saveMyHwpContents").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/convert/removeConvertContents").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/convert/errHwpConvertContents").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/convert/fileConvertStatistic").hasAnyRole("TOP_TESTER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/mathInfo/mathContentsStatistic").hasAnyRole("TOP_TESTER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mathDocs/mathDocsUsageStatistic").hasAnyRole("TOP_TESTER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/takeMembersStatistic").hasAnyRole("TOP_TESTER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/serviceCenter/takeErrReportCount").hasAnyRole("TOP_TESTER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/serviceCenter/takeErrReportByAdmin").hasAnyRole("TOP_TESTER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/serviceCenter/takeErrReportSearchBySttsAndTypeByAdmin").hasAnyRole("TOP_TESTER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/serviceCenter/replyErrorReport").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/common/imgUpload").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/common/download").permitAll()

                        .requestMatchers("/mathInfo/**").permitAll()
                        .requestMatchers("/author").hasAnyRole("user")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterAt(
                        loginRequestAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(jwtRequestAuthFilter, LoginRequestAuthFilter.class)
                .exceptionHandling(
                        customizer -> customizer.authenticationEntryPoint(authenticationEntryPoint)
                )
        ;
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
        configuration.addExposedHeader("access-token");            // 추가한 코드
        configuration.addExposedHeader("role");            // 추가한 코드
        //configuration.addExposedHeader("Set-Cookie");			// 추가한 코드
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

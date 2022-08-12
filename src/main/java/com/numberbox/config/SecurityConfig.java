package com.numberbox.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.numberbox.jwt.util.JwtAuthenticationFilter;
import com.numberbox.jwt.util.JwtUtil;
import com.numberbox.security.handler.CustomAccessDeniedHandler;
import com.numberbox.security.handler.CustomAuthenticationEntryPoint;
import com.numberbox.security.handler.LoginFailureHandler;
import com.numberbox.security.handler.LoginSuccessHandler;
import com.numberbox.security.service.CustomSecurityUsersService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
	CustomSecurityUsersService customUsersService;
	
	@Autowired
	CustomAccessDeniedHandler customAccessDeniedHandler;
	@Autowired
	CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	@Autowired
	LoginSuccessHandler loginSuccessHandler;
	@Autowired
	LoginFailureHandler loginFailureHandler;
	@Autowired
	AuthenticationManagerBuilder auth;
	
	@Autowired
	DataSource datasource;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/webapp/**");
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	super.configure(auth);
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/loginProcess").permitAll()
                .antMatchers(HttpMethod.POST, "/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/naverLogin").permitAll()
                .antMatchers(HttpMethod.GET, "/takeResource").permitAll()
                .antMatchers(HttpMethod.GET, "/takeResourceByResourceNo").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/takeMerchantUid").permitAll()
                .antMatchers(HttpMethod.GET, "/certifications/*").permitAll()
                .antMatchers(HttpMethod.GET, "/findEmail").permitAll()
                
                .antMatchers(HttpMethod.GET, "/takeMyEmail").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/confirmPassword").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/changePassword").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/changePhoneNumber").hasAnyRole("USER")
                
                .antMatchers(HttpMethod.GET, "/takeProfile").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/takeUserProfile").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/changeNickname").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/registerProfileImg").hasAnyRole("USER")
                
                .antMatchers(HttpMethod.GET, "/followingUser").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/followingCancel").hasAnyRole("USER")

                .antMatchers(HttpMethod.POST, "/mathInfo/takeWorkContentsList").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/mathInfo/takeWorkContentsListByContentsNo").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/mathInfo/takeContentsListByContentsNo").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/mathInfo/takeContentsList").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathInfo/takeMyContentsList").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathInfo/takeUserContentsList").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathInfo/takeMyRepo").hasAnyRole("USER")
                
                .antMatchers(HttpMethod.GET, "/mathInfo/takeMyWorkContents").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathInfo/takeContentsByContentsNo").hasAnyRole("USER")
                
                .antMatchers(HttpMethod.GET, "/mathInfo/myContentsDel").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathInfo/myRepoDel").hasAnyRole("USER")
                
                .antMatchers(HttpMethod.GET, "/mathInfo/likeContents").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathInfo/putInMyRepo").hasAnyRole("USER")
                
                .antMatchers(HttpMethod.POST, "/mathInfo/registerContents").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/mathInfo/makeContents").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathInfo/conSvcSttsChng").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/mathInfo/delCompContents").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/mathInfo/registerCompContents").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/mathInfo/registerResource").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/mathInfo/updateResource").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/mathInfo/takeMyResource").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/mathInfo/myResourceDel").hasAnyRole("USER")
                
                .antMatchers(HttpMethod.GET, "/mathDocs/mathDocs").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathDocs/myMathDocs").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathDocs/delMyMathDocs").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/mathDocs/similarContents").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/mathDocs/registerMathDocsPaper").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/mathDocs/mathDocsByMyMathDocsPage").hasAnyRole("USER")
                
                .antMatchers(HttpMethod.GET, "/serviceCenter/takeErrReport").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/serviceCenter/registerError").hasAnyRole("USER")
                .antMatchers(HttpMethod.GET, "/serviceCenter/takeMyErrReport").hasAnyRole("USER")
                
                .antMatchers(HttpMethod.GET, "/serviceCenter/takeErrReportCount").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/serviceCenter/takeErrReportByAdmin").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/serviceCenter/takeErrReportSearchBySttsAndTypeByAdmin").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/serviceCenter/replyErrorReport").hasAnyRole("ADMIN")
                
                .antMatchers(HttpMethod.GET, "/common/download").permitAll()
                
                .antMatchers("/mathInfo/**").permitAll()
                .antMatchers("/author").hasAnyRole("user");
          
        http
        .httpBasic().disable()
        .csrf().disable()
        .cors()
        .and()
        .userDetailsService(customUsersService)
        .formLogin().loginProcessingUrl("/loginProcess").successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
        .and()
        .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        
        
        
        /*
        http
        .httpBasic().disable()
        .cors().and()
        .csrf().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	//세션 생성하지 않음
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtAuthenticationProvider), UsernamePasswordAuthenticationFilter.class);
        */
        
        
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(customUsersService);
    }
    
    
}

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
                .antMatchers(HttpMethod.GET, "/takeResource").permitAll()
                .antMatchers(HttpMethod.POST, "/mathInfo/takeContents").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/mathInfo/takeMyContents").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/mathInfo/registerContents").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/mathInfo/registerResource").hasAnyRole("USER")
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

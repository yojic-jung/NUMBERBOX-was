package com.numberbox.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webapp/**").addResourceLocations("file:src/main/webapp/").setCachePeriod(20);
	}

	//현재 cors 설정 사실상 의미 없음, web서버와 was 같은 서버에서 동작되고
	//web서버의 로컬에서 경로에 따라 같은 서버의 was로 연결되게끔 설정 (도메인 설정하지 않음)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "http://localhost:8080", "https://nsoohak.com", "https://nsoohak.com:8080"
            		, "https://www.nsoohak.com", "https://www.nsoohak.com:8080")
            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
    }
    
    @Bean
    public ModelMapper modelMapper(){
    	ModelMapper modelMapper = new ModelMapper();
    	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);		//엄격한 타입비교(이름과 타입까지 모두 같은 경우에만)
        return modelMapper;
    }
}
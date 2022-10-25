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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedOrigins("https://nsoohak.com")
            .allowedOrigins("https://www.nsoohak.com")
            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
    }
    
    @Bean
    public ModelMapper modelMapper(){
    	ModelMapper modelMapper = new ModelMapper();
    	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);		//엄격한 타입비교(이름과 타입까지 모두 같은 경우에만)
        return modelMapper;
    }
}
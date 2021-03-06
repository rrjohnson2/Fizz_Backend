package com.jsware.fizz.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

	@Override
	public void addCorsMappings(CorsRegistry reg)
	{
		reg.addMapping("/**")
			//.allowedOrigins("**")
			.allowedMethods("POST","GET");
		
		log.info("Mapping Init");
	}

}

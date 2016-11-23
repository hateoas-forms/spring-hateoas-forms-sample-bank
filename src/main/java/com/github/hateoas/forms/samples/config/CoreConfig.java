package com.github.hateoas.forms.samples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ImportResource({ "/WEB-INF/dataAccess-config.xml" })
public class CoreConfig {

	@Bean
	public CurieProvider curieProvider() {
		return new DefaultCurieProvider("halforms", new UriTemplate("/doc/{rel}"));
	}

	@Bean
	public ConversionService defaultConversionService() {
		return new DefaultFormattingConversionService();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}

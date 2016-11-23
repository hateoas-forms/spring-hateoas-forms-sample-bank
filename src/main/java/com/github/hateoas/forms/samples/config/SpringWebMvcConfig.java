package com.github.hateoas.forms.samples.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hateoas.forms.samples.mvc.HalHttpMessageConverter;
import com.github.hateoas.forms.spring.halforms.HalFormsMessageConverter;

@Configuration
@ComponentScan({ "com.github.hateoas.forms.samples.controllers" })
@EnableWebMvc
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class SpringWebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RelProvider relProvider;

	@Autowired
	private CurieProvider curieProvider;

	@Autowired
	private MessageSourceAccessor resourceDescriptionMessageSourceAccessor;

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/index.html", "/styles/**", "/scripts/**", "/fonts/**").addResourceLocations("/", "/styles/",
				"/scripts/", "/fonts/");
	}

	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");
	}

	@Autowired
	private MessageSource messageSource;

	@Bean
	public HalHttpMessageConverter halMessageConverter() {
		return new HalHttpMessageConverter(messageSource);
	}

	@Bean
	public HalFormsMessageConverter halFormsMessageConverter() {
		HalFormsMessageConverter converter = new HalFormsMessageConverter(objectMapper, relProvider, curieProvider,
				resourceDescriptionMessageSourceAccessor);
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.parseMediaType("application/prs.hal-forms+json")));
		return converter;
	}

	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		converters.add(halMessageConverter());
		converters.add(halFormsMessageConverter());
		super.configureMessageConverters(converters);
	}

}

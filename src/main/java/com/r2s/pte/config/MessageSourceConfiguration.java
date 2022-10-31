package com.r2s.pte.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageSourceConfiguration {
	 @Bean
	    public MessageSource messageSource() {
		 ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	        messageSource.setBasenames(
	                "message/MessageSource"
	        );
	        messageSource.setDefaultEncoding("UTF-8");
	        return messageSource;
	    }
	 @Bean
	 public LocalValidatorFactoryBean getValidator() {
	     LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	     bean.setValidationMessageSource(messageSource());
	     return bean;
	 }
}

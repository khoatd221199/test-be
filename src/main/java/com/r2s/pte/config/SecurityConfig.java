package com.r2s.pte.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.r2s.pte.exception.CustomAccessDeniedHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
		.and().authorizeRequests().anyRequest().permitAll()
		.and().csrf().disable();
		http.addFilterBefore(new CustomAuthorizationFilter(), SecurityContextHolderAwareRequestFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("OPTIONS");
		configuration.addAllowedMethod("HEAD");
		configuration.addAllowedMethod("GET");
		configuration.addAllowedMethod("PUT");
		configuration.addAllowedMethod("POST");
		configuration.addAllowedMethod("DELETE");
		configuration.addAllowedMethod("PATCH");
		configuration.setAllowedOrigins(Arrays.asList("*","http://localhost:3000","https://my.boostpte.com","http://admin.boostpte.com","https://admin.boostpte.com"));
		source.registerCorsConfiguration("/**", configuration);
		return new CorsFilter(source);
	}
}

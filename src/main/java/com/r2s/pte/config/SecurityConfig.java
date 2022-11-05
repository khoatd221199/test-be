package com.r2s.pte.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
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
		http.httpBasic().and()
				.cors()
				.configurationSource(corsConfigurationSource())
				.and()
				.csrf()
				.disable()
				.formLogin()
				.disable();
	}


	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs",
				"/configuration/ui",
				"/swagger-resources/**",
				"/configuration/security",
				"/swagger-ui.html",
				"swagger-ui/index.html",
				"/webjars/**");
	}



	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source;
		source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		List<String> all = Collections.singletonList("*");
		configuration.setAllowedOrigins(all);
		configuration.setAllowedMethods(all);
		configuration.setAllowedHeaders(all);
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
//		configuration.addAllowedOrigin("*");
//		configuration.addAllowedHeader("*");
//		configuration.addAllowedMethod("OPTIONS");
//		configuration.addAllowedMethod("HEAD");
//		configuration.addAllowedMethod("GET");
//		configuration.addAllowedMethod("PUT");
//		configuration.addAllowedMethod("POST");
//		configuration.addAllowedMethod("DELETE");
//		configuration.addAllowedMethod("PATCH");
//		configuration.setAllowedOrigins(Arrays.asList("*","http://localhost:3000","https://my.boostpte.com","http://admin.boostpte.com","https://admin.boostpte.com"));
//		source.registerCorsConfiguration("/**", configuration);
//		return new CorsFilter(source);
//	}
}

package com.yesbank.config;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin")
				.password("password").authorities("ROLE_ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//	http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
		http.formLogin().disable().anonymous()
		.disable().httpBasic()
		.and().authorizeRequests().anyRequest()
		.authenticated();
		/*http.formLogin().disable().anonymous().disable().httpBasic().and().authorizeRequests().anyRequest()
				.authenticated().and().cors().configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest arg0) {
						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.addAllowedOrigin("*");
						config.setAllowCredentials(true);
						return config;
					}
				}).and().csrf().disable();
		http.csrf().disable()
        .authorizeRequests()
        //allow CORS option calls
        .antMatchers("/resources/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .and()
        .httpBasic();
*/	}

	/* @Bean
	    CorsConfigurationSource corsConfigurationSource() {
		 CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
	        UrlBasedCorsConfigurationSource source = new
	                UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	*/ 
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}

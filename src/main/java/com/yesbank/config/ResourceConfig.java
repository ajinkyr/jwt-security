package com.yesbank.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter{

	@Value("${security.oauth2.resource.id}")
	private String resourceId;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private DefaultTokenServices tokenService;
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceId)
		.tokenStore(tokenStore)
		.tokenServices(tokenService);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(new OAuthRequestedMatcher())
		.csrf().disable().anonymous().disable()
		.authorizeRequests().antMatchers(HttpMethod.OPTIONS)
		.permitAll()
		.antMatchers("/api/hello").access("hasAnyRole('USER')")
		.antMatchers("/api/admin").hasRole("ADMIN")
		.antMatchers("/api/**").authenticated();
	}
	
	private static class OAuthRequestedMatcher implements RequestMatcher {
	    public boolean matches(HttpServletRequest request) {
	        // Determine if the resource called is "/api/**"
	        String path = request.getServletPath();
	        if ( path.length() >= 5 ) {
	          path = path.substring(0, 5);
	          boolean isApi = path.equals("/api/");
	          return isApi;
	        } else return false;
	    }
	}

	
}

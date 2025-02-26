package br.com.ybardockz.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			
			.and()
			.authorizeHttpRequests(authorize -> 
				authorize
					.requestMatchers("/v1/cozinha/**").permitAll()
					.anyRequest().authenticated()
					)
			.sessionManagement(session -> 
				session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)
			.csrf(crsf -> crsf.disable());
		
		return http.build();
		
	}

}

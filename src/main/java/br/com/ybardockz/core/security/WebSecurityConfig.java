package br.com.ybardockz.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.httpBasic(basic -> {})
			
			.authorizeHttpRequests(authorize -> 
				authorize
					.anyRequest().authenticated()
					)
			.cors(cors -> cors.disable())
			.oauth2ResourceServer(resource -> 
					resource.opaqueToken(Customizer.withDefaults())
					);
		
		return http.build();
		
	}
	
}

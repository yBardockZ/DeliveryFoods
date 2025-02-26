package br.com.ybardockz.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
	
	@Bean
	UserDetailsService userDetailsService() {
		UserDetails thiago = User.builder()
				.username("thalles")
				.password(passwordEncoder().encode("123"))
				.roles("ADMIN")
				.build();
		
		UserDetails carlos = User.builder()
				.username("carlos")
				.password(passwordEncoder().encode("123"))
				.roles("ADMIN")
				.build();
				
		return new InMemoryUserDetailsManager(thiago, carlos);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}

package br.com.ybardockz.core.security;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.httpBasic(basic -> {})
			
			.authorizeHttpRequests(authorize -> 
				authorize
					.anyRequest().authenticated()
					)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.oauth2ResourceServer(resource -> 
					resource
						.jwt(jwt -> jwt
								.decoder(jwtDecoder())
								.jwtAuthenticationConverter(jwtAuthenticationConverter())
							)
					)
			.sessionManagement(session -> 
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					);
		
		return http.build();
		
	}
	
	private Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtConverter = 
				new JwtAuthenticationConverter();
		
		jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
		
		return jwtConverter;
	}
	
	private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
		return jwt -> {
			List<String> authorities = jwt.getClaimAsStringList("authorities");
				
				if (authorities == null) {
					return Collections.emptyList();
				}
					
				return authorities.stream()
				.map(authority -> new SimpleGrantedAuthority(authority))
				.collect(Collectors.toList());
		};
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8082");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
	
	@Bean
	JwtDecoder jwtDecoder() {
		try {
			String publicKeyPEM = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi8kTT8AxZtD6h3rsSi6loQAitJ27krwS6XSjDcplTzro6jgxcS2bNvyRIa/syZNhQ1mAsn7mDQXmlyhVefXwHLGv6U505M5Xd/pj94QIaJMebpNSN0ay3Lb8Q/NZVtMRoZ8FArOnMIPOF48eNrHk7SznStLHxOwgKtXJ5PJoWBQF+JnjYcWTvUZGk6RPWi6BmBEJqMVQZ7+0w6ixFS+l7I3mazLq50msKPqkqVD12Sw2wycJemyIewIme1u7Y8zq/dvlxY0RKCh/UQ1Y2BM5qCzHxYs12VrnbbOHEaSOIpyMr9ZESfjOjjRFDJaPx+YXI+LZ8mxlQiTKjhgaWpu0AQIDAQAB";
			
			byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
		
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec); 
			
			return NimbusJwtDecoder.withPublicKey(publicKey).build();
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao carregar a chave pública", e);
		}
		
	}
	
	
}

package com.crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().requestMatchers("/api/**"
//				, "/home/**").permitAll()
//				.anyRequest().authenticated();
//		http.headers().frameOptions().disable();
//		return http.build();
//	}
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ws/**","/api/**", "/home/**").permitAll()
                .anyRequest().authenticated() 
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .cors(cors -> cors.disable());;
        return http.build();
    }
    
    
}

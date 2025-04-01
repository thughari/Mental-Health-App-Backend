package com.hack4job.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hack4job.userservice.filter.JWTFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	return httpSecurity.csrf(customizer -> customizer.disable())
    			.cors(customizer -> customizer.disable())
    			.authorizeHttpRequests(request -> request.
    												requestMatchers("/api/auth/**","/api/auth/**").permitAll().
    												anyRequest().authenticated())
//    			.formLogin(Customizer.withDefaults())
    			.httpBasic(Customizer.withDefaults())
    			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    			.build();
	}
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
//    @Bean
//    UserDetailsService userDetailsService() {
//    	UserDetails user1 = User.builder()
//                .username("hari1")
//                .password(passwordEncoder().encode("111")) // Securely encode password
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("siva")
//                .password(passwordEncoder().encode("222"))
//                .roles("ADMIN")
//                .build();
//    	return new InMemoryUserDetailsManager(user1, user2);
//    }
    
    @Bean
    AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(passwordEncoder());
    	provider.setUserDetailsService(userDetailsService);
    	return provider;
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    	return 	configuration.getAuthenticationManager();
    }
	
}

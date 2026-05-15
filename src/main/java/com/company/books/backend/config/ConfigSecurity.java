package com.company.books.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurity {

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		UserDetails ariel = User.builder()
				.username("ariel")
				.password("{noop}ariel123")
				.roles("Empleado")
				.build();
		
		UserDetails admin = User.builder()
				.username("admin")
				.password("{noop}admin")
				.roles("Desarrollador")
				.build();
				
		UserDetails jose = User.builder()
				.username("jose")
				.password("{noop}jose123")
				.roles("Empleado","Jefe")
				.build();
		return new InMemoryUserDetailsManager(ariel,admin,jose);
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    	http.authorizeHttpRequests(configure -> {
    	    configure
    	    .requestMatchers(HttpMethod.GET, "/v1/libros")
    	    .hasAnyRole("Empleado", "Jefe", "Desarrollador")
    	    
    	    .requestMatchers(HttpMethod.GET, "/v1/libros/**")
    	        .hasAnyRole("Empleado", "Jefe", "Desarrollador")
    	    
    	        .requestMatchers(HttpMethod.POST, "/v1/libros")
    	        .hasAnyRole("Jefe", "Desarrollador")
    	    
    	        .requestMatchers(HttpMethod.PUT, "/v1/libros/**")
    	        .hasAnyRole("Jefe", "Desarrollador")
    	    
    	        .requestMatchers(HttpMethod.DELETE, "/v1/libros/**")
    	        .hasAnyRole("Jefe", "Desarrollador");
    	});
    	
		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf ->csrf.disable());
		return http.build();
	}
}

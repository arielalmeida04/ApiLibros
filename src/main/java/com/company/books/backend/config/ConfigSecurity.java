package com.company.books.backend.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.company.books.backend.filter.JwtReqFilter;

@Configuration
public class ConfigSecurity {


	private final JwtReqFilter jwtReqFilter;
	
	public  ConfigSecurity(@Lazy JwtReqFilter jwtReqFilter) {
		this.jwtReqFilter =  jwtReqFilter;
		
	}
	@Bean
	public UserDetailsManager userDetailsManager(DataSource datasource) {
		return new JdbcUserDetailsManager(datasource);
	}
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    	http.authorizeHttpRequests(configure -> {
    	    configure
    	    .requestMatchers(HttpMethod.GET, "/v1/libros").hasAnyRole("EMPLEADO", "JEFE")
    	    .requestMatchers(HttpMethod.GET, "/v1/libros/**").hasAnyRole("EMPLEADO", "JEFE")
    	        .requestMatchers(HttpMethod.POST, "/v1/libros").hasAnyRole("JEFE")
    	        .requestMatchers(HttpMethod.PUT, "/v1/libros/**").hasAnyRole("JEFE")
    	        .requestMatchers(HttpMethod.DELETE, "/v1/libros/**").hasAnyRole("JEFE")
    	        
    	        //CRUD con la otra entidad Categoria
    	        .requestMatchers(HttpMethod.GET, "/v1/categorias").hasAnyRole("EMPLEADO", "JEFE")
        	    .requestMatchers(HttpMethod.GET, "/v1/categorias/**").hasAnyRole("EMPLEADO", "JEFE")
        	        .requestMatchers(HttpMethod.POST, "/v1/categorias").hasAnyRole("JEFE")
        	        .requestMatchers(HttpMethod.PUT, "/v1/categorias/**").hasAnyRole("JEFE")
        	        .requestMatchers(HttpMethod.DELETE, "/v1/categorias/**").hasAnyRole("JEFE")
        	        
        	        //Acceso a las propiedades privadas o documentacion del sistema
        	        .requestMatchers("/v1/authenticate","/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll();
    	    
    	    
    	    
    	})
    	.addFilterBefore(jwtReqFilter,UsernamePasswordAuthenticationFilter.class)
    	.sessionManagement((session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    			);
    	
		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf ->csrf.disable());
		return http.build();
	}
    
    
    /*
	 * @Bean
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
				.username("Pepe")
				.password("{noop}pepe123")
				.roles("Empleado","Jefe")
				.build();
		return new InMemoryUserDetailsManager(ariel,admin,jose);
	}
*/
}

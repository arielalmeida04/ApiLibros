package com.company.books.backend.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.request.AuthRequest;
import com.company.books.backend.response.TokenResponse;
import com.company.books.backend.service.JwtService;



@RestController
@RequestMapping("/v1")
public class TokenController {
	
	  private final AuthenticationManager authenticationManager;
	  private final UserDetailsService userDetailsService;
	  private final JwtService jwtService;
	
	  private static final Logger log = LoggerFactory.getLogger(TokenController.class);
	 
	    public TokenController(AuthenticationManager authenticationManager,UserDetailsService userDetailsService,JwtService jwtService) {
	        this.authenticationManager = authenticationManager;
			this.userDetailsService = userDetailsService;
			this.jwtService = jwtService;
	    }
	

	@PostMapping("/authenticate")
	public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthRequest request){
		try {
		
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
			
		} catch (Exception e) {
			log.error("Error en obtener las listas de Libros", e);
		}	
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUser());
		final String jwt = jwtService.generateToken(userDetails);
		return ResponseEntity.ok(new TokenResponse(jwt));
	} 
}

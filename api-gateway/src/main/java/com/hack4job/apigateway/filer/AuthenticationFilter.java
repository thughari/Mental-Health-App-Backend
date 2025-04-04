package com.hack4job.apigateway.filer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.hack4job.apigateway.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	public AuthenticationFilter() {
        super(Config.class);
    }

	@Override
	public GatewayFilter apply(Config config) {
		log.info("inside AuthenticationFilter");
		return ((exchange, chain) ->{
			if(validator.isSecured.test(exchange.getRequest())) {
				//header contains token or not
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					log.info("missing Authorization header");
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header"); // Use a more descriptive message.
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if(authHeader!=null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}

				try {
					jwtUtil.validateToken(authHeader);
					log.info("provided was a valid jwt token");
				} catch (Exception e) {
					log.info("invalid token");
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT", e); // Provide specific error and exception
				}
			}
			log.info("proceeding");
			return chain.filter(exchange);
		});
	}
	
	public static class Config {

	}

}
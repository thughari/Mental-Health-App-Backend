package com.hack4job.apigateway.filer;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	
	public static final List<String> openApiEndponts = List.of("/register","/login","/eureka");
	
	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndponts
															.stream()
															.noneMatch(uri -> request.getURI().getPath().contains(uri));

}

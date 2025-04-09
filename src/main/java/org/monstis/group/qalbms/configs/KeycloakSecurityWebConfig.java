package org.monstis.group.qalbms.configs;

import org.monstis.group.qalbms.service.JwtAuthenticationManager;
import org.monstis.group.qalbms.service.JwtSecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class KeycloakSecurityWebConfig {


	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
														 JwtAuthenticationManager authManager) {
		return http
				.securityContextRepository(new JwtSecurityContextRepository(authManager))
				.authenticationManager(authManager)
				.authorizeExchange(exchanges -> exchanges
						.pathMatchers(
								"/auth/**",
								"/v3/api-docs/**",
								"/swagger-ui.html",
								"/api/register",
								"/api/verify-otp",
								"/swagger-ui/**"
						).permitAll()
						.anyExchange().authenticated()
				)
				.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
				.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.build();
	}
//	@Bean
//	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//		http.csrf(ServerHttpSecurity.CsrfSpec::disable)
//				.authorizeExchange(exchange -> exchange.pathMatchers("/admin/**").hasRole("CLIENT")
//						.pathMatchers("/user/**").hasRole("USER")
//						.pathMatchers("/api/v1/auth/refresh-token","v1/webjars/swagger-ui/index.html","v1/swagger-ui/index.html","/api/register/**","/api/verify-otp/**").permitAll()
//						.pathMatchers("/swagger-ui/**","/v3/api-docs/**","swagger-ui.html","v1/api-docs/**", "/api-docs/**", "/swagger-resources/**", "/webjars/**")
//						.permitAll()
//						.anyExchange().authenticated())
//				.oauth2ResourceServer(
//						oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
//
//		return http.build();
//	}

//	@Bean
//	public ReactiveJwtDecoder jwtDecoder() {
//
//		NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder) ReactiveJwtDecoders
//				.fromOidcIssuerLocation("http://23.239.18.240:9999/realms/master");
//
//		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer("http://23.239.18.240:9999/realms/master");
//		jwtDecoder.setJwtValidator(withIssuer);
//
//		return jwtDecoder;
//	}

//	@Bean
//	public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
//		ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
//		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//
//		grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//		grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
//
//		converter.setJwtGrantedAuthoritiesConverter(jwt -> Flux.fromIterable(grantedAuthoritiesConverter.convert(jwt)));
//
//		return converter;
//	}
}

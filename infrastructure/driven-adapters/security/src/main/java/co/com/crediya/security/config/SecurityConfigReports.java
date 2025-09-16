package co.com.crediya.security.config;

import co.com.crediya.security.handler.AccessDeniedExceptionHandler;
import co.com.crediya.security.handler.UnauthorizedExceptionHandler;
import co.com.crediya.security.helper.JwtRoleConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;

@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfigReports {

    private final JwtRoleConverter jwtRoleConverter;
    private final ReactiveJwtDecoder jwtDecoder;
    private final UnauthorizedExceptionHandler unauthorizedExceptionHandler;
    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**",
                                "/openapi/**"
                        ).permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/v1/reports").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtSpec ->
                                jwtSpec
                                        .jwtAuthenticationConverter(grantedAuthoritiesExtractor())
                                        .jwtDecoder(jwtDecoder)
                        ).authenticationEntryPoint(unauthorizedExceptionHandler)
                                .accessDeniedHandler(accessDeniedExceptionHandler)
                )
                .build();
    }

    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        return jwt -> {
            Collection<GrantedAuthority> authorities = jwtRoleConverter.convert(jwt);
            return Mono.just(new JwtAuthenticationToken(jwt, authorities));
        };
    }
}

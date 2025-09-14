package co.com.crediya.security.handler;

import co.com.crediya.model.report.exceptions.ReportUnauthorizedException;
import co.com.crediya.model.report.exceptions.enums.ExceptionMessages;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class UnauthorizedExceptionHandler implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.error( new ReportUnauthorizedException(ExceptionMessages.UNAUTHORIZED_SENT_TOKEN_INVALID.getMessage()));
    }
}

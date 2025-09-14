package co.com.crediya.model.report.user;

import reactor.core.publisher.Mono;

public interface TokenProvider {
    Mono<String> getCurrentToken();
}

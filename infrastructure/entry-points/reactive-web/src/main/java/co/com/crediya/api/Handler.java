package co.com.crediya.api;

import co.com.crediya.model.report.models.ReportResponse;
import co.com.crediya.usecase.report.ReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Handler {
    private final ReportUseCase useCase;

    public Mono<ServerResponse> getReports(ServerRequest request) {

        LocalDateTime from = request.queryParam("from")
                .map(Instant::parse)
                .map(instant -> LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
                .orElse(LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault()));

        LocalDateTime to = request.queryParam("to")
                .map(Instant::parse)
                .map(instant -> LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
                .orElse(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));

        return useCase.getReportsAndTotal(from, to)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .switchIfEmpty(ServerResponse.ok()
                        .bodyValue(new ReportResponse(BigDecimal.ZERO, 0L,List.of())));
    }
}

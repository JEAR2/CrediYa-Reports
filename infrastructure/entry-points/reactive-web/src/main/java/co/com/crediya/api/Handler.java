package co.com.crediya.api;

import co.com.crediya.api.util.HandlersResponseUtil;
import co.com.crediya.model.report.exceptions.enums.ExceptionStatusCode;
import co.com.crediya.model.report.models.ReportResponse;
import co.com.crediya.usecase.report.ReportUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation( tags = "Reports", operationId = "getReports", description = "Reports list", summary = "Reports list",
            responses = { @ApiResponse( responseCode = "201", description = "Report get successfully.", content = @Content( schema = @Schema( implementation = ReportResponse.class ) ) )

            },
            parameters = {
                    @Parameter( in = ParameterIn.HEADER, name = "Authorization", description = "Bearer token", required = true, example = "mkasjdlkjas782347812" )
            })
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
                .flatMap(response ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(HandlersResponseUtil.buildBodySuccessResponse(
                                        ExceptionStatusCode.OK.status(),
                                        response
                                ))
                )
                .switchIfEmpty(
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(HandlersResponseUtil.buildBodySuccessResponse(
                                        ExceptionStatusCode.OK.status(),
                                        new ReportResponse(BigDecimal.ZERO, 0L, List.of())
                                ))
                );
    }
}

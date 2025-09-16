package co.com.crediya.api;

import co.com.crediya.model.report.models.ReportResponse;
import co.com.crediya.usecase.report.ReportUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReportUseCase reportUseCase;
/*
    @Test
    void shouldReturnReports() {
        ReportResponse reportResponse = new ReportResponse(
                BigDecimal.valueOf(100),
                1L,
                List.of()
        );

        Mockito.when(reportUseCase.getReportsAndTotal(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(reportResponse));

        webTestClient.mutateWith(
                        mockJwt()
                                .jwt(jwt -> jwt.subject("a@a.com"))
                                .authorities(() -> "ROLE_ADMIN")
                ).get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/reports")
                        .queryParam("from", Instant.now().toString())
                        .queryParam("to", Instant.now().toString())
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.data.totalAmount").isEqualTo(100);
    }
*/
}

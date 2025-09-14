package co.com.crediya.usecase.report;

import co.com.crediya.model.report.Report;
import co.com.crediya.model.report.gateways.ReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@ExtendWith(MockitoExtension.class)
class ReportUseCaseTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportUseCase reportUseCase;

    @Test
    void execute_ShouldReturnSavedReport() {
        Report report = Report.builder()
                .pk("pk1")
                .requestId("1")
                .amount(BigDecimal.valueOf(100))
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(reportRepository.saveIfNotExists(report))
                .thenReturn(Mono.just(report));

        StepVerifier.create(reportUseCase.execute(report))
                .expectNext(report)
                .verifyComplete();

        Mockito.verify(reportRepository).saveIfNotExists(report);

    }

    @Test
    void getReportsAndTotal_ShouldCalculateTotalAmountAndCount() {
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now();

        Report report1 = Report.builder()
                .pk("pk1")
                .requestId("1")
                .amount(BigDecimal.valueOf(100))
                .createdAt(LocalDateTime.now())
                .build();

        Report report2 = Report.builder()
                .pk("pk2")
                .requestId("2")
                .amount(BigDecimal.valueOf(200))
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(reportRepository.getReportsBetween(from, to))
                .thenReturn(Flux.just(report1, report2));

        StepVerifier.create(reportUseCase.getReportsAndTotal(from, to))
                .assertNext(response -> {
                    Assertions.assertEquals(BigDecimal.valueOf(300), response.getTotalAmount());
                    Assertions.assertEquals(2, response.getTotalCount());
                })
                .verifyComplete();

        Mockito.verify(reportRepository).getReportsBetween(from, to);
    }

}
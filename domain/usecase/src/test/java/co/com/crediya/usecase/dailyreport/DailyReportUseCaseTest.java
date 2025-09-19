package co.com.crediya.usecase.dailyreport;

import co.com.crediya.model.report.report.Report;
import co.com.crediya.model.report.report.gateway.EmailGateway;
import co.com.crediya.model.report.report.gateway.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DailyReportUseCaseTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private EmailGateway emailGateway;

    @InjectMocks
    private DailyReportUseCase dailyReportUseCase;

    private Report mockReport;

    @BeforeEach
    void setUp() {
        mockReport = new Report("2",10L, BigDecimal.valueOf(5000.0));
    }


    @Test
    void shouldSendReportWhenItExists() {
        // Arrange
        Mockito.when(reportRepository.findReport())
                .thenReturn(Mono.just(mockReport));
        Mockito.when(emailGateway.sendDailyReport(mockReport))
                .thenReturn(Mono.empty());

        // Act
        Mono<Void> result = dailyReportUseCase.sendDailyReport();

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        // Verify
        verify(reportRepository).findReport();
        verify(emailGateway).sendDailyReport(mockReport);
    }

    @Test
    void shouldSendEmptyReportWhenItDoesNotExist() {
        // Arrange
        Mockito.when(reportRepository.findReport())
                .thenReturn(Mono.empty());
        Mockito.when(emailGateway.sendDailyReport(any(Report.class)))
                .thenReturn(Mono.empty());

        // Act
        Mono<Void> result = dailyReportUseCase.sendDailyReport();

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        // Verify
        verify(reportRepository).findReport();

        ArgumentCaptor<Report> reportCaptor = ArgumentCaptor.forClass(Report.class);
        verify(emailGateway).sendDailyReport(reportCaptor.capture());

        Report sentReport = reportCaptor.getValue();
        assertEquals(0, sentReport.getTotalRequestsApproved());
        assertEquals(BigDecimal.ZERO, sentReport.getTotalAmountApproved());
    }
}
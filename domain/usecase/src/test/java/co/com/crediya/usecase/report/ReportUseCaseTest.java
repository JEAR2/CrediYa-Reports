package co.com.crediya.usecase.report;

import co.com.crediya.model.report.approvedRequest.ApprovedRequest;
import co.com.crediya.model.report.approvedRequest.gateway.ApprovedRequestRepository;
import co.com.crediya.model.report.report.Report;
import co.com.crediya.model.report.report.gateway.ReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.time.Instant;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ReportUseCaseTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ApprovedRequestRepository approvedRequestRepository;


    @InjectMocks
    private ReportUseCase reportUseCase;


    private ApprovedRequest mockApprovedRequest;
    private Report mockInitialReport;

    @BeforeEach
    void setUp() {
        mockApprovedRequest = new ApprovedRequest("pk","requestId123",BigDecimal.TEN, Instant.now());
        mockInitialReport = new Report("pk",10L, BigDecimal.valueOf(10.0));
    }

    @Test
    void findReport_ShouldReturnReport() {


        Mockito.when(reportRepository.findReport())
                .thenReturn(Mono.just(mockInitialReport));

        StepVerifier.create(reportUseCase. getReport())
                .expectNext(mockInitialReport)
                .verifyComplete();

        verify(reportRepository).findReport();

    }

    @Test
    void execute_ShouldReturnSavedReport(){


        // Arrange
        Mockito.when(approvedRequestRepository.existsByRequestId(mockApprovedRequest.getRequestId()))
                .thenReturn(Mono.just(false));
        Mockito.when(approvedRequestRepository.saveApprovedRequest(mockApprovedRequest))
                .thenReturn(Mono.just(mockApprovedRequest));
        Mockito.when(reportRepository.findReport())
                .thenReturn(Mono.just(mockInitialReport));
        Mockito.when(reportRepository.saveReport(any(Report.class)))
                .thenReturn(Mono.just(mockInitialReport));

        // Act
        Mono<Void> result = reportUseCase.updateReportOnRequestApproved(mockApprovedRequest);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        // Verify interactions
        verify(approvedRequestRepository).existsByRequestId(mockApprovedRequest.getRequestId());
        verify(approvedRequestRepository).saveApprovedRequest(mockApprovedRequest);
        verify(reportRepository).findReport();
        verify(reportRepository).saveReport(any(Report.class));
    }



    @Test
    void shouldNotUpdateReportWhenRequestAlreadyExists() {


       // Arrange
        Mockito.when(approvedRequestRepository.existsByRequestId(mockApprovedRequest.getRequestId()))
                .thenReturn(Mono.just(true));

        // Act
        Mono<Void> result = reportUseCase.updateReportOnRequestApproved(mockApprovedRequest);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        // Verify interactions
        verify(approvedRequestRepository).existsByRequestId(mockApprovedRequest.getRequestId());
        verify(approvedRequestRepository, never()).saveApprovedRequest(any(ApprovedRequest.class));
        verify(reportRepository, never()).findReport();
        verify(reportRepository, never()).saveReport(any(Report.class));
    }


}
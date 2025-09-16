package co.com.crediya.usecase.report;

import co.com.crediya.model.report.approvedRequest.ApprovedRequest;
import co.com.crediya.model.report.approvedRequest.gateway.ApprovedRequestRepository;
import co.com.crediya.model.report.report.Report;
import co.com.crediya.model.report.report.gateway.ReportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.crediya.usecase.report.utils.ReportUtils.buildEmptyReport;
import static co.com.crediya.usecase.report.utils.ReportUtils.buildIncrementedReport;

@RequiredArgsConstructor
public class ReportUseCase {
    private final ReportRepository reportRepository;
    private final ApprovedRequestRepository approvedRequestRepository;

    public Mono<Report> getReport() {
        return reportRepository.findReport();
    }

    public Mono<Void> updateReportOnRequestApproved(ApprovedRequest request) {
        return approvedRequestRepository.existsByRequestId(request.getRequestId())
                .filter(exists -> !exists)
                .flatMap(ignore -> approvedRequestRepository.saveApprovedRequest(request)
                        .then(reportRepository.findReport()
                                .defaultIfEmpty(buildEmptyReport())
                                .map(report -> buildIncrementedReport(report, request.getAmount()))
                                .flatMap(reportRepository::saveReport)
                        )
                )
                .then();
    }

}

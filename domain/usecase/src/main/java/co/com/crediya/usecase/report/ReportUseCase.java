package co.com.crediya.usecase.report;

import co.com.crediya.model.report.Report;
import co.com.crediya.model.report.gateways.ReportRepository;
import co.com.crediya.model.report.models.ReportResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ReportUseCase {
    private final ReportRepository reportRepository;

    public Mono<Report> execute(Report report) {
        return reportRepository.saveIfNotExists(report);
    }

    public Mono<ReportResponse> getReportsAndTotal(LocalDateTime from, LocalDateTime to) {
        return reportRepository.getReportsBetween(from, to)
                .collectList()
                .map(list -> {
                    BigDecimal totalAmount = list.stream()
                            .map(Report::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    long totalCount = list.size();
                    return new ReportResponse(totalAmount, totalCount, list);
                });
    }
}

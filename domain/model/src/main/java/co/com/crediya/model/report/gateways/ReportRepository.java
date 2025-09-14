package co.com.crediya.model.report.gateways;

import co.com.crediya.model.report.Report;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface ReportRepository {
    Flux<Report> getReportsBetween(LocalDateTime from, LocalDateTime to);
    Mono<Report> saveIfNotExists(Report report);
}

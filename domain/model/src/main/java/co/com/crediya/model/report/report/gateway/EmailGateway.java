package co.com.crediya.model.report.report.gateway;

import co.com.crediya.model.report.report.Report;
import reactor.core.publisher.Mono;

public interface EmailGateway {
    Mono<Void> sendDailyReport(Report report);
}

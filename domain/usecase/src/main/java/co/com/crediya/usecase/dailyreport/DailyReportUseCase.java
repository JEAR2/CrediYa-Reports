package co.com.crediya.usecase.dailyreport;

import co.com.crediya.model.report.report.gateway.EmailGateway;
import co.com.crediya.model.report.report.gateway.ReportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import static co.com.crediya.usecase.report.utils.ReportUtils.buildEmptyReport;

@RequiredArgsConstructor
public class DailyReportUseCase {
    private final ReportRepository reportRepository;
    private final EmailGateway emailGateway;

    public Mono<Void> sendDailyReport() {
        return reportRepository.findReport()
                .defaultIfEmpty(buildEmptyReport())
                .flatMap(emailGateway::sendDailyReport);
    }


}

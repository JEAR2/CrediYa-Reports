package co.com.crediya.sqs.listener;

import co.com.crediya.usecase.dailyreport.DailyReportUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Slf4j
@Component("dailyReportProcessor")
@RequiredArgsConstructor
public class ReportSQSProcessor implements Function<Message, Mono<Void>> {
    private final DailyReportUseCase reportUseCase;
    @Override
    public Mono<Void> apply(Message message) {
        log.info("Evento recibido para enviar reporte diario");
        return reportUseCase.sendDailyReport()
                .doOnSuccess(v -> log.info("Reporte enviado OK"))
                .doOnError(e -> log.error("Error enviando reporte diario", e));
    }
}

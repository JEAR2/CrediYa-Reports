package co.com.crediya.sqs.listener;

import co.com.crediya.model.report.Report;
import co.com.crediya.sqs.listener.dtos.ApprovedEventDTO;
import co.com.crediya.usecase.report.ReportUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final ReportUseCase reportUseCase;
    private final ObjectMapper objectMapper;
    @Override
    public Mono<Void> apply(Message message) {
        log.info("Received SQS message for Report: {}", message.body());

        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), ApprovedEventDTO.class))
                .flatMap(event -> {
                    Report report = Report.builder()
                            .pk("REPORT#" + event.state())
                            .requestId(event.requestId())
                            .amount(event.amount())
                            .createdAt(LocalDateTime.now())
                            .build();

                    return reportUseCase.execute(report);
                })
                .doOnSuccess(r -> log.info("Report saved OK"))
                .doOnError(e -> log.error("Error saving report", e))
                .then();
    }
}

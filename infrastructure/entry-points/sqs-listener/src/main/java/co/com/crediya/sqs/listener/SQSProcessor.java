package co.com.crediya.sqs.listener;

import co.com.crediya.sqs.listener.dtos.ApprovedEventDTO;
import co.com.crediya.sqs.listener.mapper.SqsMapper;
import co.com.crediya.usecase.report.ReportUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Component("approvedProcessor")
@Slf4j
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final ReportUseCase reportUseCase;
    private final ObjectMapper objectMapper;
    private final SqsMapper mapper;
    @Override
    public Mono<Void> apply(Message message) {
        log.info("Received SQS message for Report: {}", message.body());

        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), ApprovedEventDTO.class))
                .map(mapper::toDomain)
                .flatMap(reportUseCase::updateReportOnRequestApproved)
                .doOnSuccess(r -> log.info("Report saved OK"))
                .doOnError(e -> log.error("Error saving report", e))
                .then();
    }
}

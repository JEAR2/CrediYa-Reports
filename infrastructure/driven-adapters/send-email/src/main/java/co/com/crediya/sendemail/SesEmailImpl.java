package co.com.crediya.sendemail;

import co.com.crediya.model.report.report.Report;
import co.com.crediya.model.report.report.gateway.EmailGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SesEmailImpl implements EmailGateway {

    private final SesClient ses;

    @Override
    public Mono<Void> sendDailyReport(Report report) {
        return Mono.fromRunnable(() -> {
            String subject = NotificationConstants.SUBJECT_TEMPLATE;
            String textBody = String.format(NotificationConstants.BODY_TEMPLATE,
                    report.getTotalRequestsApproved(),
                    report.getTotalAmountApproved());

            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses("correo@gmail.com").build())
                    .message(Message.builder()
                            .subject(Content.builder().data(subject).build())
                            .body(Body.builder()
                                    .text(Content.builder().data(textBody).build())
                                    .build())
                            .build())
                    .source("correo@gmail.com")
                    .build();

            ses.sendEmail(emailRequest);
        });
    }
}

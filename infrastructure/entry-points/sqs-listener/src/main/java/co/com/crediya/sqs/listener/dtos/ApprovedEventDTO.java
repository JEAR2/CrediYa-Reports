package co.com.crediya.sqs.listener.dtos;

import java.math.BigDecimal;
import java.time.Instant;

public record ApprovedEventDTO(
        String requestId,
        BigDecimal amount,
        Instant approvedAt) {
}

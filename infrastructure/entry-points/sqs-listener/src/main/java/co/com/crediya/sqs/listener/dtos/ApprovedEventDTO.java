package co.com.crediya.sqs.listener.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ApprovedEventDTO(String eventId,
                               String requestId,
                               BigDecimal amount,
                               String state,
                               LocalDateTime createdAt) {
}

package co.com.crediya.model.report;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Report {
    private String pk;             // "REPORT#APPROVED"
    private String sk;             // timestamp ISO
    private String eventId;        // para idempotencia
    private BigDecimal amount;
    private LocalDateTime createdAt;

}

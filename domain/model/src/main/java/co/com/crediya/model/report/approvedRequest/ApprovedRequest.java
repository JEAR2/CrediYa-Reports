package co.com.crediya.model.report.approvedRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovedRequest {
    private String pk;
    private String requestId;
    private BigDecimal amount;
    private Instant approvedAt;
}

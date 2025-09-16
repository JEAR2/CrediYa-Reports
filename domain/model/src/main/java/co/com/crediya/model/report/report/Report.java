package co.com.crediya.model.report.report;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Report {
    private String pk;
    private Long totalRequestsApproved;
    private BigDecimal totalAmountApproved;


}

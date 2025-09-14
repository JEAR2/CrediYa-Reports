package co.com.crediya.model.report.models;

import co.com.crediya.model.report.Report;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ReportResponse {
    private BigDecimal totalAmount;
    private long totalCount;
    private List<Report> records;
}

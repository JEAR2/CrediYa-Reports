package co.com.crediya.usecase.report.utils;

import co.com.crediya.model.report.report.Report;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class ReportUtils {
    public static Report buildEmptyReport() {
        return Report.builder()
                .totalRequestsApproved(0L)
                .totalAmountApproved(BigDecimal.ZERO)
                .build();
    }

    public static Report buildIncrementedReport(Report currentReport, BigDecimal amountToAdd) {
        return Report.builder()
                .totalRequestsApproved(currentReport.getTotalRequestsApproved() + 1)
                .totalAmountApproved(currentReport.getTotalAmountApproved().add(amountToAdd))
                .build();
    }
}

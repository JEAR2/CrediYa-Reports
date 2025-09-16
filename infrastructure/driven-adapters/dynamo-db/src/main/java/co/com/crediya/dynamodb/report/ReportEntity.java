package co.com.crediya.dynamodb.report;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

@DynamoDbBean
public class ReportEntity {
    private String pk;
    private Long totalRequestsApproved;
    private BigDecimal totalAmountApproved;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("pk")
    public String getPk() { return pk; }
    public void setPk(String pk) { this.pk = pk; }

    @DynamoDbAttribute("totalRequestsApproved")
    public Long getTotalRequestsApproved() { return totalRequestsApproved; }
    public void setTotalRequestsApproved(Long totalRequestsApproved) { this.totalRequestsApproved = totalRequestsApproved; }

    @DynamoDbAttribute("totalAmountApproved")
    public BigDecimal getTotalAmountApproved() {
        return totalAmountApproved;
    }

    public void setTotalAmountApproved(BigDecimal totalAmountApproved) {
        this.totalAmountApproved = totalAmountApproved;
    }

}

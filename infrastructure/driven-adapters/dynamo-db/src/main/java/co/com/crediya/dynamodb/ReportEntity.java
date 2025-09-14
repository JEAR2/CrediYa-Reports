package co.com.crediya.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;
import java.time.LocalDateTime;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class ReportEntity {
    private String pk;
    private String sk;
    private String eventId;
    //private String requestId;
    private String amount;
    private LocalDateTime createdAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("pk")
    public String getPk() { return pk; }
    public void setPk(String pk) { this.pk = pk; }

    @DynamoDbSortKey
    @DynamoDbAttribute("sk")
    public String getSk() { return sk; }
    public void setSk(String sk) { this.sk = sk; }

    @DynamoDbAttribute("eventId")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /*@DynamoDbAttribute("requestId")
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }*/

    @DynamoDbAttribute("amount")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @DynamoDbAttribute("createAt")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

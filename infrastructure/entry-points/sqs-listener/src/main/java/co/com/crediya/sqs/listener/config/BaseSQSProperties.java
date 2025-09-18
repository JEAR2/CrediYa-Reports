package co.com.crediya.sqs.listener.config;

public interface BaseSQSProperties {
    String region();
    String endpoint();
    String queueUrl();
    Integer waitTimeSeconds();
    Integer visibilityTimeoutSeconds();
    Integer maxNumberOfMessages();
    Integer numberOfThreads();
}

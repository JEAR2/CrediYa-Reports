package co.com.crediya.sqs.listener.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "entrypoint.sqs.approved")
public record ApprovedSQSProperties(
        String region,
        String endpoint,
        String queueUrl,
        @DefaultValue("10") Integer waitTimeSeconds,
        @DefaultValue("30") Integer visibilityTimeoutSeconds,
        @DefaultValue("10") Integer maxNumberOfMessages,
        @DefaultValue("2") Integer numberOfThreads) implements BaseSQSProperties {
}

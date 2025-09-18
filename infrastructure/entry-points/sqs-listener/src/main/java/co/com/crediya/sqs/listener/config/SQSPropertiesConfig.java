package co.com.crediya.sqs.listener.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        ApprovedSQSProperties.class,
        ReportSQSProperties.class
})
public class SQSPropertiesConfig {
}

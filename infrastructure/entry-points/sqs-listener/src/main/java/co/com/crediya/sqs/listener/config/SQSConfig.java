package co.com.crediya.sqs.listener.config;

import co.com.crediya.sqs.listener.helper.SQSListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.Message;

import java.net.URI;
import java.util.function.Function;
@Configuration
public class SQSConfig {
    @Bean
    public SQSListener approvedSqsListener(
            @Qualifier("approvedSqsClient") SqsAsyncClient approvedClient,
            ApprovedSQSProperties approvedProperties,
            @Qualifier("approvedProcessor") Function<Message, Mono<Void>> approvedFn) {
        return SQSListener.builder()
                .client(approvedClient)
                .properties(approvedProperties)
                .processor(approvedFn)
                .build()
                .start();
    }

    @Bean
    public SQSListener dailyReportSqsListener(
            @Qualifier("dailySqsClient") SqsAsyncClient dailyClient,
            ReportSQSProperties dailyProperties,
            @Qualifier("dailyReportProcessor") Function<Message, Mono<Void>> dailyFn) {
        return SQSListener.builder()
                .client(dailyClient)
                .properties(dailyProperties)
                .processor(dailyFn)
                .build()
                .start();
    }


    // client para approved
    @Bean
    public SqsAsyncClient approvedSqsClient(ApprovedSQSProperties properties, MetricPublisher publisher) {
        return buildClient(properties, publisher);
    }

    // client para daily
    @Bean
    public SqsAsyncClient dailySqsClient(ReportSQSProperties properties, MetricPublisher publisher) {
        return buildClient(properties, publisher);
    }

    private SqsAsyncClient buildClient(BaseSQSProperties properties, MetricPublisher publisher) {
        return SqsAsyncClient.builder()
                .endpointOverride(resolveEndpoint(properties))
                .region(Region.of(properties.region()))
                .overrideConfiguration(o -> o.addMetricPublisher(publisher))
                .credentialsProvider(getProviderChain())
                .build();
    }

    private AwsCredentialsProviderChain getProviderChain() {
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
                .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .addCredentialsProvider(ProfileCredentialsProvider.create())
                .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
                .build();
    }

    protected URI resolveEndpoint(BaseSQSProperties properties) {
        return properties.endpoint() != null ? URI.create(properties.endpoint()) : null;
    }
}

package co.com.crediya.dynamodb.config;

import co.com.crediya.dynamodb.ReportEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDbAsyncClient amazonDynamoDB(@Value("${aws.dynamodb.endpoint}") String endpoint,
                                              @Value("${aws.region}") String region,
                                              @Value("${aws.dynamodb.accessKey}") String accessKey,
                                              @Value("${aws.dynamodb.secretKey}") String secretKey) {
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedAsyncClient(DynamoDbAsyncClient client) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(client)
                .build();
    }
    @Bean
    public DynamoDbAsyncTable<ReportEntity> reportTable(DynamoDbEnhancedAsyncClient client) {
        return client.table("reports", TableSchema.fromBean(ReportEntity.class));
    }
}

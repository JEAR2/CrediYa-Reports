package co.com.crediya.dynamodb;

import co.com.crediya.dynamodb.helper.TemplateAdapterOperations;
import co.com.crediya.model.report.Report;
import co.com.crediya.model.report.exceptions.ReportBadReportException;
import co.com.crediya.model.report.exceptions.enums.ExceptionMessages;
import co.com.crediya.model.report.gateways.ReportRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.time.LocalDateTime;


@Repository
public class DynamoDBReportAdapter extends TemplateAdapterOperations<Report, String, ReportEntity > implements ReportRepository {

    private final DynamoDbAsyncTable<ReportEntity> table;

    public DynamoDBReportAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper, DynamoDbAsyncTable<ReportEntity> table) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, Report.class), "reports");
        this.table = table;
    }

    @Override
    public Flux<Report> getReportsBetween(LocalDateTime from, LocalDateTime to) {
        return Flux.from(table.query(r ->
                        r.queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue("REPORT#APPROVED")))
                ).items())
                .filter(report -> !report.getCreatedAt().isBefore(from) && !report.getCreatedAt().isAfter(to)).map(super::toModel);
    }


    @Override
    public Mono<Report> saveIfNotExists(Report report) {

            ReportEntity reportEntity = mapper.map(report, ReportEntity.class);

            Expression condition = Expression.builder()
                    .expression("attribute_not_exists(pk) AND attribute_not_exists(sk)")
                    .build();

            return Mono.fromFuture(() ->
                            table.putItem(r -> r.item(reportEntity)
                                    .conditionExpression(condition))
                    )
                    .thenReturn(report)
                    .onErrorResume(
                            software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException.class,
                            e -> Mono.error(new ReportBadReportException(ExceptionMessages.REQUEST_ALREADY_APPROVED.getMessage())));
    }


}

package co.com.crediya.dynamodb.report;

import co.com.crediya.dynamodb.helper.TemplateAdapterOperations;
import co.com.crediya.model.report.report.Report;
import co.com.crediya.model.report.report.gateway.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;

import java.time.LocalDateTime;


@Slf4j
@Repository
public class DynamoDBReportAdapter extends TemplateAdapterOperations<Report, String, ReportEntity > implements ReportRepository {

    private static final String REPORT_PK = "REPORT";


    public DynamoDBReportAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, Report.class), "reports");
    }


    @Override
    public Mono<Report> findReport() {
        log.info("Fetching report with PK={}", REPORT_PK);
        return getById(REPORT_PK)
                .doOnNext(r -> log.info("Fetched report: {}", r))
                .doOnError(e -> log.error("Error buscando Report", e));
    }

    @Override
    public Mono<Report> saveReport(Report report) {
        log.info("saveReport called with parameter: {}", report);
        report.setPk(REPORT_PK);
        return save(report)
                .doOnNext(r -> log.info("Saved report: {}", r))
                .doOnError(e -> log.error("Error guardando Report", e));
    }


}

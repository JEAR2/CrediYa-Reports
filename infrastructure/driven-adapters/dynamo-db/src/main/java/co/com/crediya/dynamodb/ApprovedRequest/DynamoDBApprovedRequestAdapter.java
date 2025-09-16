package co.com.crediya.dynamodb.ApprovedRequest;

import co.com.crediya.dynamodb.helper.TemplateAdapterOperations;
import co.com.crediya.model.report.approvedRequest.gateway.ApprovedRequestRepository;
import co.com.crediya.model.report.exceptions.ReportBadReportException;
import co.com.crediya.model.report.exceptions.enums.ExceptionMessages;
import co.com.crediya.model.report.report.gateway.ReportRepository;
import co.com.crediya.model.report.approvedRequest.ApprovedRequest;
import co.com.crediya.model.report.report.Report;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.time.LocalDateTime;


@Slf4j
@Repository
public class DynamoDBApprovedRequestAdapter extends TemplateAdapterOperations<ApprovedRequest, String, ApprovedRequestEntity> implements ApprovedRequestRepository{

    private static final String APPROVED_REQUEST_PK = "APPROVED-REQUEST";


    public DynamoDBApprovedRequestAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, ApprovedRequest.class), "ApprovedRequest");
    }


    @Override
    public Mono<Boolean> existsByRequestId(String requestId) {
        log.debug("existsByRequestId called with parameter: requestId={}", requestId);
        return getById(APPROVED_REQUEST_PK, requestId)
                .hasElement()
                .doOnNext(exists -> log.debug("request exists? {}", exists));
    }

    @Override
    public Mono<ApprovedRequest> saveApprovedRequest(ApprovedRequest approvedRequest) {
        log.info("saveApprovedRequest called with parameter: {}", approvedRequest);
        approvedRequest.setPk(APPROVED_REQUEST_PK);
        return save(approvedRequest)
                .doOnNext(l -> log.info("Saved approved request: {}", l));
    }
}

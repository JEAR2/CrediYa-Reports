package co.com.crediya.model.report.approvedRequest.gateway;

import co.com.crediya.model.report.approvedRequest.ApprovedRequest;
import reactor.core.publisher.Mono;


public interface ApprovedRequestRepository {

    Mono<Boolean> existsByRequestId(String loanId);
    Mono<ApprovedRequest> saveApprovedRequest(ApprovedRequest approvedRequest);

}

package co.com.crediya.sqs.listener.mapper;

import co.com.crediya.model.report.approvedRequest.ApprovedRequest;
import co.com.crediya.model.report.report.Report;
import co.com.crediya.sqs.listener.dtos.ApprovedEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface SqsMapper {
    ApprovedEventDTO eventToResponse(Report report);
    ApprovedRequest toDomain(ApprovedEventDTO dto);
}

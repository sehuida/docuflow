package com.docuflow.app.core.dto.response;

import com.docuflow.app.core.enums.ApprovalStatus;
import com.docuflow.app.core.enums.DocumentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApprovalLineResponseDTO {
    private Long id;
    private String approver;
    private Integer approvalOrder;
    private ApprovalStatus status;
}
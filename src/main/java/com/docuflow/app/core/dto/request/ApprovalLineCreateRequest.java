package com.docuflow.app.core.dto.request;

import lombok.Getter;

@Getter
public class ApprovalLineCreateRequest {
    private Long approverId;
    private Integer approvalOrder;
}
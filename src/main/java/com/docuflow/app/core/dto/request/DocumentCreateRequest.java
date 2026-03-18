package com.docuflow.app.core.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class DocumentCreateRequest {
    private String title;
    private String content;
    private List<ApprovalLineCreateRequest> approvalLines;
}
package com.docuflow.app.core.dto.response;

import com.docuflow.app.core.enums.DocumentStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DocumentResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private DocumentStatus status;
    private List<ApprovalLineResponseDTO> approvalLines;

}


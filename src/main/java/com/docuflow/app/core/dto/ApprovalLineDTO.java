package com.docuflow.app.core.dto;

import com.docuflow.app.core.entity.Document;
import com.docuflow.app.core.entity.User;
import com.docuflow.app.core.enums.ApprovalStatus;
import com.docuflow.app.core.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalLineDTO {

    private Long id;
    private Document document;
    private User approver;
    private ApprovalStatus status;

}


package com.docuflow.app.core.entity;

import com.docuflow.app.core.enums.ApprovalStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "approval_lines")
public class ApprovalLine {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Document document;

    @ManyToOne
    private User approver;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;
}
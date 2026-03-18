package com.docuflow.app.core.entity;

import com.docuflow.app.core.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "approval_lines")
public class ApprovalLine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    private User approver;

    private Integer approvalOrder;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private String rejectReason;

    public static ApprovalLine create(Document document, User approver, int order) {
        ApprovalLine line = new ApprovalLine();
        line.document = document;
        line.approver = approver;
        line.approvalOrder = order;
        line.status = ApprovalStatus.PENDING;
        return line;
    }

    public void approve() {
        validatePending();
        this.status = ApprovalStatus.APPROVED;
    }

    public void reject(String reason) {
        validatePending();
        this.status = ApprovalStatus.REJECTED;
        this.rejectReason = reason;
    }

    private void validatePending() {
        if (this.status != ApprovalStatus.PENDING) {
            throw new IllegalStateException("이미 처리됨");
        }
    }
}
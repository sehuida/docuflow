package com.docuflow.app.core.entity;

import com.docuflow.app.core.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "documents")
public class Document {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalLine> approvalLines = new ArrayList<>();

    public static Document create(String title, String content, User author) {
        Document doc = new Document();
        doc.title = title;
        doc.content = content;
        doc.author = author;
        doc.status = DocumentStatus.IN_PROGRESS;
        return doc;
    }

    public void addApprovalLine(ApprovalLine line) {
        this.approvalLines.add(line);
    }

    public void approve() {
        if (this.status != DocumentStatus.IN_PROGRESS) {
            throw new IllegalStateException("승인 불가 상태");
        }
        this.status = DocumentStatus.APPROVED;
    }

    public void reject() {
        if (this.status != DocumentStatus.IN_PROGRESS) {
            throw new IllegalStateException("반려 불가 상태");
        }
        this.status = DocumentStatus.REJECTED;
    }
}
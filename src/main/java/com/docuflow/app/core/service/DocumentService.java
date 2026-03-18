package com.docuflow.app.core.service;

import com.docuflow.app.core.dto.ApprovalLineDTO;
import com.docuflow.app.core.dto.DocumentCreateDTO;
import com.docuflow.app.core.dto.DocumentDTO;
import com.docuflow.app.core.dto.request.ApprovalLineCreateRequest;
import com.docuflow.app.core.dto.request.DocumentCreateRequest;
import com.docuflow.app.core.dto.request.RejectRequest;
import com.docuflow.app.core.dto.response.ApprovalLineResponseDTO;
import com.docuflow.app.core.dto.response.DocumentResponseDTO;
import com.docuflow.app.core.entity.ApprovalLine;
import com.docuflow.app.core.entity.Document;
import com.docuflow.app.core.entity.User;
import com.docuflow.app.core.enums.ApprovalStatus;
import com.docuflow.app.core.enums.DocumentStatus;
import com.docuflow.app.core.repository.ApprovalLineRepository;
import com.docuflow.app.core.repository.DocumentRepository;
import com.docuflow.app.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ApprovalLineRepository approvalLineRepository;
    private final UserRepository userRepository;

    // 문서 생성
    public DocumentResponseDTO createDocument(DocumentCreateRequest request, String username) {

        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Document document = Document.create(request.getTitle(), request.getContent(), author);

        // DTO → Entity 변환
        request.getApprovalLines().forEach(lineDto -> {

            User approver = userRepository.findById(lineDto.getApproverId())
                    .orElseThrow(() -> new IllegalArgumentException("결재자 없음"));

            ApprovalLine line = ApprovalLine.create(
                    document,
                    approver,
                    lineDto.getApprovalOrder()
            );

            document.addApprovalLine(line);
        });

        documentRepository.save(document);

        return toResponse(document);
    }

    // 승인
    public void approveDocument(Long lineId, String username) {

        ApprovalLine line = approvalLineRepository.findById(lineId)
                .orElseThrow(() -> new IllegalArgumentException("결재라인 없음"));

        validateApprover(line, username);
        validateOrder(line);

        line.approve();

        updateDocumentStatus(line.getDocument());
    }

    // 반려
    public void rejectDocument(Long lineId, RejectRequest request, String username) {

        ApprovalLine line = approvalLineRepository.findById(lineId)
                .orElseThrow(() -> new IllegalArgumentException("결재라인 없음"));

        validateApprover(line, username);

        line.reject(request.getReason());
        line.getDocument().reject();
    }

    // =========================
    // 검증
    // =========================

    private void validateApprover(ApprovalLine line, String username) {
        if (!line.getApprover().getUsername().equals(username)) {
            throw new IllegalStateException("권한 없음");
        }
    }

    private void validateOrder(ApprovalLine line) {

        List<ApprovalLine> previous =
                approvalLineRepository.findByDocumentIdAndApprovalOrderLessThan(
                        line.getDocument().getId(),
                        line.getApprovalOrder()
                );

        boolean ok = previous.stream()
                .allMatch(l -> l.getStatus() == ApprovalStatus.APPROVED);

        if (!ok) throw new IllegalStateException("순서 안됨");
    }

    private void updateDocumentStatus(Document document) {
        boolean allApproved = document.getApprovalLines().stream()
                .allMatch(l -> l.getStatus() == ApprovalStatus.APPROVED);

        if (allApproved) document.approve();
    }

    private DocumentResponseDTO toResponse(Document doc) {

        List<ApprovalLineResponseDTO> lines = doc.getApprovalLines().stream()
                .map(line -> ApprovalLineResponseDTO.builder()
                        .id(line.getId())
                        .approver(line.getApprover().getUsername())
                        .approvalOrder(line.getApprovalOrder())
                        .status(line.getStatus())
                        .build())
                .toList();

        return DocumentResponseDTO.builder()
                .id(doc.getId())
                .title(doc.getTitle())
                .content(doc.getContent())
                .author(doc.getAuthor().getUsername())
                .status(doc.getStatus())
                .approvalLines(lines)
                .build();
    }
}
package com.docuflow.app.core.service;

import com.docuflow.app.core.dto.DocumentCreateDTO;
import com.docuflow.app.core.dto.response.DocumentResponseDTO;
import com.docuflow.app.core.entity.ApprovalLine;
import com.docuflow.app.core.entity.Document;
import com.docuflow.app.core.entity.User;
import com.docuflow.app.core.enums.ApprovalStatus;
import com.docuflow.app.core.enums.DocumentStatus;
import com.docuflow.app.core.repository.ApprovalLineRepository;
import com.docuflow.app.core.repository.DocumentRepository;
import com.docuflow.app.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    @Autowired private DocumentRepository documentRepository;
    @Autowired private ApprovalLineRepository approvalLineRepository;
    @Autowired private UserRepository userRepository;

    public DocumentResponseDTO createDocument(DocumentCreateDTO dto, String username) {

        User author = userRepository.findByUsername(username)
                .orElseThrow();

        Document document = new Document();
        document.setTitle(dto.getTitle());
        document.setContent(dto.getContent());
        document.setAuthor(author);
        document.setStatus(DocumentStatus.PENDING);

        document = documentRepository.save(document);

        return DocumentResponseDTO.builder()
                .id(document.getId())
                .title(document.getTitle())
                .content(document.getContent())
                .author(author.getUsername())
                .status(document.getStatus())
                .build();
    }

    public void approve(Long lineId) {
        ApprovalLine line = approvalLineRepository.findById(lineId).orElseThrow();
        line.setStatus(ApprovalStatus.APPROVED);
        approvalLineRepository.save(line);

        Document doc = line.getDocument();
        boolean allApproved = doc.getApprovalLines().stream()
                .allMatch(l -> l.getStatus() == ApprovalStatus.APPROVED);
        if (allApproved) doc.setStatus(DocumentStatus.APPROVED);
        documentRepository.save(doc);
    }
}

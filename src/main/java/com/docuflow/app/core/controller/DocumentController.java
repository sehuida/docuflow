package com.docuflow.app.core.controller;

import com.docuflow.app.core.dto.DocumentCreateDTO;
import com.docuflow.app.core.dto.request.DocumentCreateRequest;
import com.docuflow.app.core.dto.request.RejectRequest;
import com.docuflow.app.core.dto.response.DocumentResponseDTO;
import com.docuflow.app.core.entity.Document;
import com.docuflow.app.core.entity.User;
import com.docuflow.app.core.service.DocumentService;
import com.docuflow.app.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentResponseDTO> create(
            @RequestBody DocumentCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(
                documentService.createDocument(request, userDetails.getUsername())
        );
    }

    @PostMapping("/approval-lines/{lineId}/approve")
    public ResponseEntity<Void> approve(
            @PathVariable Long lineId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        documentService.approveDocument(lineId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approval-lines/{lineId}/reject")
    public ResponseEntity<Void> reject(
            @PathVariable Long lineId,
            @RequestBody RejectRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        documentService.rejectDocument(lineId, request, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
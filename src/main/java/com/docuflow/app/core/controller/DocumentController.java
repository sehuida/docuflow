package com.docuflow.app.core.controller;

import com.docuflow.app.core.dto.DocumentCreateDTO;
import com.docuflow.app.core.dto.response.DocumentResponseDTO;
import com.docuflow.app.core.entity.Document;
import com.docuflow.app.core.entity.User;
import com.docuflow.app.core.service.DocumentService;
import com.docuflow.app.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired private DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentResponseDTO> create(
            @RequestBody DocumentCreateDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        DocumentResponseDTO response = documentService.createDocument(dto, userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve/{lineId}")
    public ResponseEntity<Void> approve(@PathVariable Long lineId) {
        documentService.approve(lineId);
        return ResponseEntity.ok().build();
    }
}


package com.docuflow.app.core.mapper;

import com.docuflow.app.core.dto.response.DocumentResponseDTO;
import com.docuflow.app.core.entity.Document;
public class DocumentMapper {

    public static DocumentResponseDTO toResponse(Document document) {

        return DocumentResponseDTO.builder()
                .id(document.getId())
                .title(document.getTitle())
                .content(document.getContent())
                .author(
                        document.getAuthor() != null
                                ? document.getAuthor().getUsername()
                                : null
                )
                .status(document.getStatus())
                .build();
    }
}

package com.docuflow.app.core.dto;

import com.docuflow.app.core.entity.User;
import com.docuflow.app.core.enums.DocumentStatus;
import com.docuflow.app.core.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.PropertyValues;

@Getter
@Setter
public class DocumentDTO {

    private Long id;
    private String title;
    private String content;
    private DocumentStatus status;
    private User author;

}


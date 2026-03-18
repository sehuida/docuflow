package com.docuflow.app.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DocumentCreateDTO {

    private String title;
    private String content;

    // 결재자 ID 목록
    private List<Long> approverIds;
}
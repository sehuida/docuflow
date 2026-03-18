package com.docuflow.app.core.repository;

import com.docuflow.app.core.entity.ApprovalLine;
import com.docuflow.app.core.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Long> {

    List<ApprovalLine> findByDocumentIdAndApprovalOrderLessThan(Long documentId, Integer approvalOrder);
}
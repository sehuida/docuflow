package com.docuflow.app.core.repository;

import com.docuflow.app.core.entity.Document;
import com.docuflow.app.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> { }
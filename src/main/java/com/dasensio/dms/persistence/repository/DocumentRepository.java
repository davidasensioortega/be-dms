package com.dasensio.dms.persistence.repository;

import com.dasensio.dms.persistence.model.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<DocumentEntity, String> {

    List<DocumentEntity> findByOwner(String owner);

}
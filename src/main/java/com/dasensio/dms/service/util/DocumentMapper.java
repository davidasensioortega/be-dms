package com.dasensio.dms.service.util;

import com.dasensio.dms.domain.Document;
import com.dasensio.dms.persistence.model.DocumentEntity;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    Document toDocument(DocumentEntity documentEntity);

    DocumentEntity toDocumentEntity(Document document);

    Collection<Document> toDocuments(Collection<DocumentEntity> documents);

}
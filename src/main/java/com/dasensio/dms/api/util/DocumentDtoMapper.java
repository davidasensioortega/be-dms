package com.dasensio.dms.api.util;

import com.dasensio.dms.api.dto.DocumentDto;
import com.dasensio.dms.domain.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface DocumentDtoMapper {

    @Mapping(target = "owner", ignore = true)
    Document toDocument(DocumentDto documentDto);

    DocumentDto toDocumentDto(Document document);

    Collection<DocumentDto> toDocumentsDto(Collection<Document> documents);

}
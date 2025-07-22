// src/main/java/com/nckh/motelroom/service/DocumentService.java
package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.entity.DocumentDto;
import com.nckh.motelroom.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    DocumentDto uploadDocument(Long idPost, MultipartFile file);
    Document getDocument(String documentId);
    List<String> getDocumentsByIdPost(Long idPost);
    void deleteAllDocuments(Long idPost);
    List<DocumentDto> getDocumentDTOsByIdPost(Long idPost);
}
// src/main/java/com/nckh/motelroom/repository/DocumentRepository.java
package com.nckh.motelroom.repository;

import com.nckh.motelroom.model.Document;
import com.nckh.motelroom.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {
    List<Document> findDocumentByPost(Post post);
}
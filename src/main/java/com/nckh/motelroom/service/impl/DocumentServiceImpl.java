// src/main/java/com/nckh/motelroom/service/impl/DocumentServiceImpl.java
package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.DocumentDto;
import com.nckh.motelroom.exception.DataNotFoundException;
import com.nckh.motelroom.exception.MyCustomException;
import com.nckh.motelroom.mapper.DocumentMapper;
import com.nckh.motelroom.model.Document;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.repository.DocumentRepository;
import com.nckh.motelroom.repository.PostRepository;
import com.nckh.motelroom.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentMapper documentMapper;
    private final DocumentRepository documentRepository;
    private final PostRepository postRepository;

    // Các định dạng file được phép
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".pdf", ".docx", ".ppt", ".pptx");

    @Override
    public DocumentDto uploadDocument(Long idPost, MultipartFile file) {
        Optional<Post> post = postRepository.findById(idPost);
        if (post.isPresent()) {
            // Kiểm tra định dạng file
            String fileName = file.getOriginalFilename();
            if (!isValidFileType(fileName)) {
                throw new MyCustomException("Chỉ cho phép upload file .pdf, .docx, .ppt, .pptx");
            }

            Document document = storeDocument(idPost, file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/document/")
                    .path(document.getId())
                    .toUriString();
            return new DocumentDto(document.getId(), document.getFileName(),
                    file.getContentType(), fileDownloadUri, idPost);
        } else {
            throw new DataNotFoundException("Không tìm thấy bài đăng có ID " + idPost);
        }
    }

    @Override
    public Document getDocument(String documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy tài liệu có id " + documentId));
    }

    @Override
    public List<String> getDocumentsByIdPost(Long idPost) {
        List<String> uri = new ArrayList<>();
        Optional<Post> post = postRepository.findById(idPost);
        if (post.isPresent()) {
            List<Document> documents = documentRepository.findDocumentByPost(post.get());
            for (Document document : documents) {
                uri.add(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/document/")
                        .path(document.getId())
                        .toUriString());
            }
        }
        return uri;
    }

    @Override
    public void deleteAllDocuments(Long idPost) {
        Optional<Post> post = postRepository.findById(idPost);
        if (post.isPresent()) {
            List<Document> documents = documentRepository.findDocumentByPost(post.get());
            documentRepository.deleteAll(documents);
        } else {
            throw new DataNotFoundException("Không tìm thấy bài đăng có ID " + idPost);
        }
    }

    @Override
    public List<DocumentDto> getDocumentDTOsByIdPost(Long idPost) {
        Optional<Post> post = postRepository.findById(idPost);
        if (post.isPresent()) {
            List<Document> documents = documentRepository.findDocumentByPost(post.get());
            List<DocumentDto> documentDtos = new ArrayList<>();
            for (Document document : documents) {
                DocumentDto documentDto = documentMapper.toDto(document);
                documentDto.setUri(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/document/")
                        .path(document.getId())
                        .toUriString());
                documentDtos.add(documentDto);
            }
            return documentDtos;
        } else {
            throw new DataNotFoundException("Không tìm thấy bài đăng có ID " + idPost);
        }
    }

    private Document storeDocument(Long idPost, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new DataNotFoundException("Tên file không hợp lệ " + fileName);
            }
            Optional<Post> post = postRepository.findById(idPost);
            Document document = new Document(fileName, file.getContentType(), file.getBytes(), post.get());
            return documentRepository.save(document);
        } catch (Exception e) {
            throw new MyCustomException("Lỗi khi lưu tài liệu " + fileName + "!");
        }
    }

    private boolean isValidFileType(String fileName) {
        if (fileName == null) return false;
        String lowerFileName = fileName.toLowerCase();
        return ALLOWED_EXTENSIONS.stream().anyMatch(lowerFileName::endsWith);
    }
}
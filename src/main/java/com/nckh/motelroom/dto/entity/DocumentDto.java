// src/main/java/com/nckh/motelroom/dto/entity/DocumentDto.java
package com.nckh.motelroom.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentDto {
    private String id;
    private String fileName;
    private String fileType;
    private String uri;
    private Long idPost;
}
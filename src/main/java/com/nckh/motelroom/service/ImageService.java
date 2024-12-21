package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.entity.ImageDto;
import com.nckh.motelroom.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImageDto uploadImage(Long idPost, MultipartFile file);

    Image storeImage(Long idPost, MultipartFile file);

    Image getImage(Long idPost);

    List<String> getImagesByPost(Long idPost);

    void deleteAllImages(Long idPost);

    List<ImageDto> getImagesDtoByPost(Long idPost);
}

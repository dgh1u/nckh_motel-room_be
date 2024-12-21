package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.ImageDto;
import com.nckh.motelroom.exception.DataNotFoundException;
import com.nckh.motelroom.mapper.ImageMapper;
import com.nckh.motelroom.model.Image;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.repository.ImageRepository;
import com.nckh.motelroom.repository.PostRepository;
import com.nckh.motelroom.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    ImageMapper imageMapper;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public ImageDto uploadImage(Long idPost, MultipartFile file) {
        Optional<Post> post = postRepository.findById(idPost);
        if (post.isPresent()) {
            Image image = storeImage(idPost, file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/image")
                    .path(image.getId())
                    .toUriString();
            return new ImageDto(image.getId(), image.getFileName(), file.getContentType(), fileDownloadUri, idPost);
        }else{
            throw new DataNotFoundException("I can't not found postID " + idPost);
        }
    }

    @Override
    public Image storeImage(Long idPost, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                throw new DataNotFoundException("I can't found file name in " + fileName);
            }
            Optional<Post> post = postRepository.findById(idPost);
            Image image = new Image(fileName, file.getContentType(), file.getBytes(), post.get());
            return imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException("Error while i handle save image " +fileName +"!"+ e);
        }
    }

    @Override
    public Image getImage(Long idPost) {
        return imageRepository.findById(idPost).orElseThrow(()->new DataNotFoundException("I can't not found postID " + idPost));
    }

    @Override
    public List<String> getImagesByPost(Long idPost) {
        List<String> uri = new ArrayList<>();
        Optional<Post> post = postRepository.findById(idPost);
        List<Image> images = imageRepository.findImageByPost(post.get());
        for (Image image : images) {
            uri.add(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/image")
                    .path(image.getId())
            .toUriString());
        }
        return uri;
    }

    @Override
    public void deleteAllImages(Long idPost) {
        Optional<Post> post = postRepository.findById(idPost);
        if (post.isPresent()) {
            List<Image> images = imageRepository.findImageByPost(post.get());
            imageRepository.deleteAll(images);
        }else{
            throw new DataNotFoundException("I can't not found postID " + idPost);
        }
    }

    @Override
    public List<ImageDto> getImagesDtoByPost(Long idPost) {
        Optional<Post> post = postRepository.findById(idPost);
        if (post.isPresent()) {
            List<Image> images = imageRepository.findImageByPost(post.get());
            List<ImageDto> imageDtos = new ArrayList<>();
            for (Image image : images) {
                ImageDto imageDto = imageMapper.toDto(image);
                imageDto.setUri(Base64.getEncoder().encodeToString(image.getData()));
                imageDtos.add(imageDto);
            }
            return imageDtos;
        }else{
            throw new DataNotFoundException("I can't not found postID " + idPost);
        }
    }
}

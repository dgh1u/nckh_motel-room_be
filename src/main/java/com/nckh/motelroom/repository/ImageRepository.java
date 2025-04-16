package com.nckh.motelroom.repository;

import com.nckh.motelroom.model.Image;
import com.nckh.motelroom.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> findImageByPost(Post post);
}

package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.model.Post;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toPostDto(Post post);
    Post toPost(PostDto postDto);
}

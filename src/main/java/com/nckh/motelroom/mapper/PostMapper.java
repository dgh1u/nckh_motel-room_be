package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.request.post.CreatePostRequest;
import com.nckh.motelroom.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toPostDto(Post post);
    Post toPost(PostDto postDto);

    // Chuyển đổi từ CreatePostRequest sang Post
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approved", ignore = true)
    @Mapping(target = "del", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "notApproved", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "accomodation", ignore = true)
    @Mapping(target = "user", ignore = true)
    Post createRequestDtoToPost(CreatePostRequest createPostRequest);
}

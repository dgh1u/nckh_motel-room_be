package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.CommentDto;
import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.request.post.CreatePostRequest;
import com.nckh.motelroom.dto.response.post.CreatePostResponse;
import com.nckh.motelroom.dto.response.post.UpdatePostResponse;
import com.nckh.motelroom.model.Comment;
import com.nckh.motelroom.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AccommodationMapper.class, CommentMapper.class})
public interface PostMapper {

    @Mapping(target = "userDTO", source = "user")
    @Mapping(target = "accomodationDTO", source = "accomodation")
//    @Mapping(target = "commentDTOS", source = "comments")
//    @Mapping(target = "imageStrings", expression = "java(post.getImages() != null ? post.getImages().stream().map(Image::getUrl).collect(Collectors.toList()) : Collections.emptyList())")
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

    @Mapping(source = "post.accomodation" , target = "accomodationDTO")
    @Mapping(source = "post.user" , target = "userDTO")
    UpdatePostResponse toUpdatePostResponse(Post post);

    @Mapping(target = "user", source = "post.user.email")  // Ánh xạ từ user sang email thay vì username
    @Mapping(target = "accomodationId", source = "post.accomodation.id")  // Ánh xạ từ accomodation sang id
    @Mapping(target = "createAt", source = "post.createAt")  // Ánh xạ createAt
    @Mapping(target = "lastUpdate", source = "post.lastUpdate")  // Ánh xạ lastUpdate
    CreatePostResponse toCreatePostResponse(Post post);
}

package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.request.comment.CreateCommentRequest;
import com.nckh.motelroom.dto.request.comment.UpdateCommentRequest;
import com.nckh.motelroom.model.Comment;
import com.nckh.motelroom.dto.entity.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring",uses = {UserMapper.class})
public interface CommentMapper {

    // Chuyển từ CreateCommentRequest sang Comment
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment toCreateComment(CreateCommentRequest request);

    // Chuyển từ UpdateCommentRequest sang Comment
    // Có thể không ánh xạ id, user, post vì đã có ở entity hiện tại
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment toUpdateComment(UpdateCommentRequest request);

    // Chuyển từ Comment sang CommentDTO
    @Mapping(target = "idPost", source = "post.id")
    @Mapping(target = "userDTO", source = "user")
    CommentDto toCommentDTO(Comment comment);
}

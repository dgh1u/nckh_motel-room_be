package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.CommentDto;
import com.nckh.motelroom.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper {
    @Mapping(target = "userDTO", source = "user")
    CommentDto toCommentDto(Comment comment);
    Comment toComment(CommentDto commentDto);
}

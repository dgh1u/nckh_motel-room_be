package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.CommentDto;
import com.nckh.motelroom.model.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toCommentDto(Comment comment);
    Comment toComment(CommentDto commentDto);
}

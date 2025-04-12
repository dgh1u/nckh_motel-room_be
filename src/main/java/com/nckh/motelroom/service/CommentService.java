package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.request.comment.CreateCommentRequest;
import com.nckh.motelroom.dto.request.comment.GetCommentRequest;
import com.nckh.motelroom.dto.request.comment.UpdateCommentRequest;
import com.nckh.motelroom.model.Comment;
import com.nckh.motelroom.dto.entity.CommentDto;
import com.nckh.motelroom.repository.custom.CustomCommentQuery;
import com.nckh.motelroom.repository.custom.CustomUserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentService {
    Page<Comment> getAllComment(CustomCommentQuery.CommentFilterParam param, PageRequest pageRequest);
    CommentDto createComment(CreateCommentRequest request,String email);
    CommentDto updateComment(UpdateCommentRequest request,String email);
    void deleteComment(Long id);
}

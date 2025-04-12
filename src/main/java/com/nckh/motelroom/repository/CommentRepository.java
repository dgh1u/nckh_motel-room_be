package com.nckh.motelroom.repository;

import com.nckh.motelroom.model.Action;
import com.nckh.motelroom.model.Comment;
import com.nckh.motelroom.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>,JpaSpecificationExecutor<Comment> {
    List<Comment> findCommentsByPostId(Long post_id);
    Page<Comment> findAllByPost(Post post, Pageable pageable);
}

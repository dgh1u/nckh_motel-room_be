package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.model.Comment;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.mapper.CommentMapper;
import com.nckh.motelroom.dto.entity.CommentDto;
import com.nckh.motelroom.repository.CommentRepository;
import com.nckh.motelroom.repository.PostRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.repository.custom.CustomCommentQuery;
import com.nckh.motelroom.repository.custom.CustomUserQuery;
import com.nckh.motelroom.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nckh.motelroom.exception.AuthenticateException;
import com.nckh.motelroom.dto.request.comment.CreateCommentRequest;
import com.nckh.motelroom.dto.request.comment.GetCommentRequest;
import com.nckh.motelroom.dto.request.comment.UpdateCommentRequest;
import com.nckh.motelroom.exception.DataNotFoundException;

import java.util.Optional;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;


    private final PostRepository postRepository;


    private final UserRepository userRepository;

    // Tiêm CommentMapper thay cho modelMapper

    private final CommentMapper commentMapper;

    @Override
    public Page<Comment> getAllComment(CustomCommentQuery.CommentFilterParam param, PageRequest pageRequest) {
        Specification<Comment> specification = CustomCommentQuery.getFilterComment(param);
        return commentRepository.findAll(specification, pageRequest);
    }

        @Override
        public CommentDto createComment(CreateCommentRequest request, String email) {
            Optional<Post> postOpt = postRepository.findById(request.getIdPost());
            if (postOpt.isPresent()) {
                // Sử dụng commentMapper để chuyển CreateCommentRequest sang Comment
                Comment comment = commentMapper.toCreateComment(request);
                comment.setLastUpdate(LocalDateTime.now());
                Optional<User> userOpt = userRepository.findByEmail(email);
                if (!userOpt.isPresent()) {
                    throw new DataNotFoundException("Người dùng không tồn tại");
                }
                comment.setUser(userOpt.get());
                // Thiết lập post từ postOpt
                comment.setPost(postOpt.get());
                commentRepository.save(comment);
                CommentDto commentDTO = commentMapper.toCommentDTO(comment);
                // Nếu cần ánh xạ thêm cho userDTO, bạn có thể thực hiện ở đây
                return commentMapper.toCommentDTO(comment);
            } else {
                throw new DataNotFoundException("Không tồn tại post id " + request.getIdPost());
            }
        }

    @Override
    @Transactional
    public CommentDto updateComment(UpdateCommentRequest request, String email) {
        Optional<Comment> commentOpt = commentRepository.findById(request.getId());
        if (!commentOpt.isPresent()) {
            throw new DataNotFoundException("Comment id " + request.getId() + " không tồn tại!");
        }
        Comment comment = commentOpt.get();


        // Ánh xạ các field từ UpdateCommentRequest sang Comment (user và post bị ignore theo mapping)
        Comment updateData = commentMapper.toUpdateComment(request);

        // Cập nhật các field cần thiết từ updateData vào đối tượng comment hiện có
        comment.setContent(updateData.getContent());
        comment.setRate(updateData.getRate());
        comment.setLastUpdate(LocalDateTime.now());

        commentRepository.save(comment);
        return commentMapper.toCommentDTO(comment);
    }



    @Override
    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment không tồn tại"));
        commentRepository.delete(comment);
    }
}

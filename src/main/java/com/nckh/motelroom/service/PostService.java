package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.entity.SearchDto;
import com.nckh.motelroom.dto.request.post.CreatePostRequest;
import com.nckh.motelroom.dto.request.post.UpdatePostRequest;
import com.nckh.motelroom.dto.response.post.CreatePostResponse;
import com.nckh.motelroom.dto.response.post.UpdatePostResponse;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.repository.custom.CustomPostQuery;
import com.nckh.motelroom.repository.custom.CustomUserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<Post> getAllPost(CustomPostQuery.PostFilterParam param, PageRequest pageRequest);

    PostDto getPostById(Long id);

    CreatePostResponse createPost(CreatePostRequest createPostRequest, String email);

    UpdatePostResponse updatePost(Long id, UpdatePostRequest updatePostRequest, String name);

    PostDto hidePost(Long id);

    String deletePostByAdmin(Long id);

    String ApprovePost(Long idPost, String usernameApprove, boolean isApprove);

    PostDto updatePostById(Long id, PostDto postDto);

    void deletePost(Long id);

    Page<PostDto> searchPostByMaps(SearchDto searchForm, int page, int sort);

    Page<PostDto> getPostWaitingApprove( int page);
}

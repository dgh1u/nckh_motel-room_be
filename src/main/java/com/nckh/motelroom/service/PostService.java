package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.entity.SearchDto;
import com.nckh.motelroom.dto.request.post.CreatePostRequest;
import com.nckh.motelroom.dto.request.post.UpdatePostRequest;
import com.nckh.motelroom.dto.response.post.*;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.repository.custom.CustomPostQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PostService {
    Page<Post> getAllPost(CustomPostQuery.PostFilterParam param, PageRequest pageRequest);

    PostDto getPostById(Long id);

    CreatePostResponse createPost(CreatePostRequest createPostRequest, String email);

    UpdatePostResponse updatePost(Long id, UpdatePostRequest updatePostRequest, String name);

    HiddenPostResponse hidePost(Long id);

    DeletePostResponse deletePostByAdmin(Long id);

    ApprovePostResponse ApprovePost(Long idPost, String usernameApprove, boolean isApprove);

    Page<PostDto> searchPostByMaps(SearchDto searchForm, int page, int sort);

    Page<PostDto> getPostWaitingApprove( int page);

}

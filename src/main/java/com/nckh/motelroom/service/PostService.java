package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.entity.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDto> getAllPost(Pageable page);

    Page<PostDto> getPostByApproved(boolean bool, int page);

    Page<PostDto> getPostByIdUser(long idUser, int page);

    Page<PostDto> getPostByUserEmail(String email, Pageable page);

    PostDto getPostById(Long id);

    PostDto createPost(PostDto postDTO, String name);

    PostDto updatePost(Long id, PostDto postDTO, String name);

    PostDto hidePost(Long id);

    Page<PostDto> getMotelPost(boolean bool, int page, int sort);

    String deletePostByAdmin(Long id);

    PostDto ApprovePost(Long idPost, String usernameApprove, boolean isApprove);

    PostDto updatePostById(Long id, PostDto postDto);

    void deletePost(Long id);

    Page<PostDto> searchPost(SearchDto searchForm, int page, int sort);

    Page<PostDto> searchPostByMaps(SearchDto searchForm, int page, int sort);

    Page<PostDto> getPostWaitingApprove( int page);
}

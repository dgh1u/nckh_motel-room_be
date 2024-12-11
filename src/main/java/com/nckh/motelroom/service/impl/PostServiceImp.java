package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.entity.SearchDto;
import com.nckh.motelroom.exception.DataNotFoundException;
import com.nckh.motelroom.mapper.AccommodationMapper;
import com.nckh.motelroom.mapper.PostMapper;
import com.nckh.motelroom.model.Accomodation;
import com.nckh.motelroom.model.District;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.model.enums.ActionName;
import com.nckh.motelroom.repository.DistrictRepository;
import com.nckh.motelroom.repository.PostRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.service.ActionService;
import com.nckh.motelroom.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImp implements PostService {

    //Inject Service


    //Inject Repository into class
    private PostRepository postRepository;

    private UserRepository userRepository;

    private DistrictRepository districtRepository;

    //Some Mapper in this
    private final PostMapper postMapper;

    private final AccommodationMapper accommodationMapper;
    private final ActionService actionService;

    @Override
    public Page<PostDto> getAllPost(Pageable page) {
        try {
            return postRepository.findAll(page).map(postMapper::toPostDto);
        }catch (RuntimeException e){
            throw new DataNotFoundException("Không có bài viết nào được tìm thấy! " + e.getMessage());
        }
    }

    @Override
    public Page<PostDto> getPostByApproved(boolean bool, int page) {
        return null;
    }

    @Override
    public Page<PostDto> getPostByIdUser(long idUser, int page) {
        return null;
    }

    @Override
    public Page<PostDto> getPostByUserEmail(String email, Pageable page) {
        return null;
    }

    @Override
    public PostDto getPostById(Long id) {
        Optional<PostDto> postDto = postRepository.findById(id).map(postMapper::toPostDto);
        if(postDto.isPresent()){
            return postDto.get();
        }else{
            throw new DataNotFoundException("Không tìm thấy bài viết theo id đã cho");
        }
    }

    @Override
    public PostDto createPost(PostDto postDto, String email) {
        try{
            Optional<User> user = userRepository.findByEmail(email);
            if(user.isPresent()){
                Post post = postMapper.toPost(postDto);
                post.setUser(user.get());
                Accomodation accomodation = accommodationMapper.toAccomodation(postDto.getAccomodationDTO());
                accomodation.setPost(post);
                Optional<District> district = districtRepository.findDistrictById(postDto.getAccomodationDTO().getIdDistrict());
                accomodation.setDistrict(district.get());
                post.setAccomodation(accomodation);
                post.getAccomodation().setStatus(true);
                postRepository.save(post);
                actionService.createAction(post, user.get(), ActionName.CREATE);
                postDto = postMapper.toPostDto(post);
                postDto.setAccomodationDTO(accommodationMapper.toAccomodationDto(accomodation));
                return postDto;
            }else{
                throw new DataNotFoundException("Không Tìm Thấy UserId " + postDto.getUserDTO().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDTO, String name) {
        return null;
    }

    @Override
    public PostDto hidePost(Long id) {
        return null;
    }

    @Override
    public Page<PostDto> getMotelPost(boolean bool, int page, int sort) {
        return null;
    }

    @Override
    public String deletePostByAdmin(Long id) {
        return "";
    }

    @Override
    public PostDto ApprovePost(Long idPost, String usernameApprover, boolean isApprove) {
        return null;
    }

    @Override
    public PostDto updatePostById(Long id, PostDto postDto) {
        return null;
    }

    @Override
    public void deletePost(Long id) {

    }

    @Override
    public Page<PostDto> searchPost(SearchDto searchDto, int page, int sort) {
        return null;
    }

    @Override
    public Page<PostDto> searchPostByMaps(SearchDto searchForm, int page, int sort) {
        return null;
    }

    @Override
    public Page<PostDto> getPostWaitingApprove(int page) {
        return null;
    }
}

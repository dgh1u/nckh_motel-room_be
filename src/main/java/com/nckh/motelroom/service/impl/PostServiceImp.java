package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.CommentDto;
import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.entity.SearchDto;
import com.nckh.motelroom.exception.DataNotFoundException;
import com.nckh.motelroom.mapper.AccommodationMapper;
import com.nckh.motelroom.mapper.PostMapper;
import com.nckh.motelroom.mapper.UserMapper;
import com.nckh.motelroom.model.*;
import com.nckh.motelroom.model.enums.ActionName;
import com.nckh.motelroom.repository.DistrictRepository;
import com.nckh.motelroom.repository.PostRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    //Inject Service
    //Inject Repository into class
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final DistrictRepository districtRepository;

    private final ImageServiceImpl imageServiceImpl;

    private final ActionServiceImp actionService;

    //Some Mapper in this
    private final PostMapper postMapper;

    private final AccommodationMapper accommodationMapper;

    private final UserMapper userMapper;

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
        Page<Post> postPage;
        if (bool) {
            postPage = postRepository.findAllByApprovedAndNotApprovedAndDel(
                    true, false,false, PageRequest.of(page, 12, Sort.by("createAt").descending()));
        }else{
            postPage = postRepository.findAllByApprovedAndNotApproved(
                    false, true, PageRequest.of(page, 12, Sort.by("createAt").descending()));
        }
        return postPage.map(postMapper::toPostDto);
    }

    @Override
    public Page<PostDto> getPostByIdUser(long idUser, int page) {
        Optional<User> userOptional = userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            return postRepository.findByUser(userOptional.get(),
                    PageRequest.of(page, 12, Sort.by("createAt").descending())).map(postMapper::toPostDto);
        }else{
            throw new DataNotFoundException("I can't found any User_id like " + idUser);
        }
    }

    @Override
    public Page<PostDto> getPostByUserEmail(String email, Pageable page) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return postRepository.findAllByUser_EmailAndDelAndApproved(email, false, true , page).map(postMapper::toPostDto);
        }else{
            throw new DataNotFoundException("I can't found any User_id like " + email);
        }
    }

    @Override
    public PostDto getPostById(Long id) {
        Optional<Post> post = postRepository.findPostById(id);
        if(post.isPresent()){
            PostDto postDto = postMapper.toPostDto(post.get());
            List<CommentDto> commentDtos = new ArrayList<>();
            List<String> images;
            images = imageServiceImpl.getImagesByPost(id);
            postDto.setImageStrings(images);
            postDto.setCommentDTOS(commentDtos);
            postDto.setUserDTO(userMapper.toUserDto(post.get().getUser()));
            postDto.getUserDTO().setB64(null);
            return postDto;
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
                post.setCreateAt(LocalDateTime.now());
                post.setLastUpdate(LocalDateTime.now());
                post.setUser(user.get());
                post.setDel(false);
                post.setApproved(false);
                post.setNotApproved(false);
                // gan vao accomodation
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
        try{
            Optional<Post> postOptional = postRepository.findPostById(id);
            if(postOptional.isPresent()){
                if(postOptional.get().getUser().getEmail().equals(name)){
                    postDTO.setId(id);
                    postDTO.getAccomodationDTO().setId(postOptional.get().getAccomodation().getId());
                    postDTO.setCreateAt(postOptional.get().getCreateAt());
                    // tao post moi tu postDto
                    postOptional = Optional.of(postMapper.toPost(postDTO));
                    Optional<User> user = userRepository.findById(postDTO.getUserDTO().getId());
                    postOptional.get().setUser(user.get());
                    // tao accommodation tu postDTO
                    Accomodation accomodation = accommodationMapper.toAccomodation(postDTO.getAccomodationDTO());
                    accomodation.setPost(postOptional.get());
                    accomodation.setId(postDTO.getAccomodationDTO().getId());
                    Optional<District> district = districtRepository.findDistrictById(postDTO.getAccomodationDTO().getIdDistrict());
                    accomodation.setDistrict(district.get());
                    postOptional.get().setAccomodation(accomodation);
                    postOptional.get().setLastUpdate(LocalDateTime.now());

                    postRepository.save(postOptional.get());
                    postDTO = postMapper.toPostDto(postOptional.get());
                    postDTO.setAccomodationDTO(accommodationMapper.toAccomodationDto(accomodation));
                    postDTO.setUserDTO(userMapper.toUserDto(user.get()));
                    return postDTO;
                }else{
                    throw new AccessDeniedException("You don't have role");
                }
            }else{
                throw new DataNotFoundException("I can't found any post_id like " + id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PostDto hidePost(Long id) {
        try {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) {
                post.get().setDel(true);
                postRepository.save(post.get());
                PostDto postDTO = postMapper.toPostDto(post.get());
                return postDTO;
            } else{
                throw new DataNotFoundException("I can't found any post_id like " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<PostDto> getMotelPost(boolean bool, int page, int sort) {
        try {
            Page<Post> postPage = null;
            //Get Motel
            if (bool) {
                if (sort == 1)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            true, PageRequest.of(page, 10, Sort.by("accomodation.price").ascending()), false);
                if (sort == 2)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            true, PageRequest.of(page, 10, Sort.by("accomodation.price").descending()), false);
                if (sort == 3)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            true, PageRequest.of(page, 10, Sort.by("accomodation.acreage").ascending()), false);
                if (sort == 4)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            true, PageRequest.of(page, 10, Sort.by("accomodation.acreage").descending()), false);
                if (sort == 5)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            true, PageRequest.of(page, 10, Sort.by("createAt").descending()), false);
            } else { //Get House
                if (sort == 1)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            false, PageRequest.of(page, 10, Sort.by("accomodation.price").ascending()), false);
                if (sort == 2)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            false, PageRequest.of(page, 10, Sort.by("accomodation.price").descending()), false);
                if (sort == 3)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            false, PageRequest.of(page, 10, Sort.by("accomodation.acreage").ascending()), false);
                if (sort == 4)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            false, PageRequest.of(page, 10, Sort.by("accomodation.acreage").descending()), false);
                if (sort == 5)
                    postPage = postRepository.findAllByApprovedAndNotApprovedAndAndAccomodation_MotelAndDel(true, false,
                            false, PageRequest.of(page, 10, Sort.by("createAt").descending()), false);
            }
            return postPage.map(postMapper::toPostDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deletePostByAdmin(Long id) {
        try {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) {
                postRepository.delete(post.get());
                return "Admin đã xóa post id " + id;
            } else
                throw new DataNotFoundException("Không tìm thấy post id " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PostDto ApprovePost(Long idPost, String usernameApprove, boolean isApprove) {
        try {
            Optional<Post> post = postRepository.findById(idPost);
            if (!post.isPresent())
                throw new DataNotFoundException("Không tìm thấy post id " + idPost);
            if (isApprove) {
                Optional<User> user = userRepository.findByEmail(usernameApprove);
                post.get().setApproved(true);
                post.get().setNotApproved(false);
                actionService.createAction(post.get(), user.get(), ActionName.APPROVE);
//                applicationEventPublisher.publishEvent(new NotificationEvent(this, post.get()));
            } else {
                Optional<User> user = userRepository.findByEmail(usernameApprove);
                post.get().setNotApproved(true);
                post.get().setApproved(false);
                actionService.createAction(post.get(), user.get(), ActionName.BLOCK);
//                applicationEventPublisher.publishEvent(new NotificationEvent(this, post.get()));
            }
            postRepository.save(post.get());
            return postMapper.toPostDto(post.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//        try {
//            Specification<Post> spec = new PostSpecification(searchForm);
//            Page<Post> postPage = null;
//            if (sort == 1)
//                postPage = postRepository.findAll(spec, PageRequest.of(page, 10, Sort.by("accomodation.price").ascending()));
//            if (sort == 2)
//                postPage = postRepository.findAll(spec, PageRequest.of(page, 10, Sort.by("accomodation.price").descending()));
//            if (sort == 3)
//                postPage = postRepository.findAll(spec, PageRequest.of(page, 10, Sort.by("accomodation.acreage").ascending()));
//            if (sort == 4)
//                postPage = postRepository.findAll(spec, PageRequest.of(page, 10, Sort.by("accomodation.acreage").descending()));
//            if (sort == 5)
//                postPage = postRepository.findAll(spec, PageRequest.of(page, 10, Sort.by("createAt").descending()));
//            Page<PostDTO> postDTOPage = postPage.map(post -> {
//                PostDTO postDTO = postToPostDTO(post);
//                return postDTO;
//            });
//
//            return postDTOPage;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

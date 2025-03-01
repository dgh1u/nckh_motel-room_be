package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.CommentDto;
import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.entity.SearchDto;
import com.nckh.motelroom.dto.request.post.CreatePostRequest;
import com.nckh.motelroom.dto.request.post.UpdatePostRequest;
import com.nckh.motelroom.dto.response.post.UpdatePostResponse;
import com.nckh.motelroom.exception.DataNotFoundException;
import com.nckh.motelroom.mapper.AccommodationMapper;
import com.nckh.motelroom.mapper.PostMapper;
import com.nckh.motelroom.mapper.UserMapper;
import com.nckh.motelroom.model.*;
import com.nckh.motelroom.model.enums.ActionName;
import com.nckh.motelroom.repository.AccomodationRepository;
import com.nckh.motelroom.repository.DistrictRepository;
import com.nckh.motelroom.repository.PostRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    private final AccomodationRepository accomodationRepository;

    private final ImageServiceImpl imageServiceImpl;

    private final ActionServiceImp actionService;

 ;   //Some Mapper in this
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
    @Transactional
    public PostDto createPost(CreatePostRequest createPostRequest, String email) {
        try{
            Optional<User> user = userRepository.findByEmail(email);
            if(user.isPresent()){
                //create post
                Post post = postMapper.createRequestDtoToPost(createPostRequest);
                post.setCreateAt(LocalDateTime.now());
                post.setLastUpdate(LocalDateTime.now());
                post.setUser(user.get());
                post.setDel(false);
                post.setApproved(false);
                post.setNotApproved(false);
                //tao accomodation
                Accomodation accomodation = accommodationMapper.toAccomodation(createPostRequest.getAccomodation());
                accomodation.setPost(post);
                // accômdation lưu tại đây
                Accomodation accomodationSaved = accomodationRepository.save(accomodation);
                post.setAccomodation(accomodationSaved);
                post.setUser(user.get());
                Post postSaved = postRepository.save(post);
                actionService.createAction(post, user.get(), ActionName.CREATE);
                return postMapper.toPostDto(postSaved);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public UpdatePostResponse updatePost(Long id, UpdatePostRequest updatePostRequest, String name) {
        try{
            //Lấy Post ra và xử lý
            Optional<Post> postOptional = postRepository.findPostById(id);
            Optional<District> districtOptional = districtRepository.findDistrictById(updatePostRequest.getAccomodation().getIdDistrict());
            Accomodation accomodation = accommodationMapper.toAccomodation(updatePostRequest.getAccomodation());

            districtOptional.ifPresent(district -> {
                accomodation.setDistrict(district);
                accomodation.setXCoordinate(district.getXCoordinate());
                accomodation.setYCoordinate(district.getYCoordinate());
            });

            if(postOptional.isPresent()){
                Post post = postOptional.get();

                post.setTitle(updatePostRequest.getTitle());
                post.setContent(updatePostRequest.getContent());
                post.setDel(updatePostRequest.isDel());
                post.setLastUpdate(LocalDateTime.now());

                post.setAccomodation(accomodation);
                accomodation.setPost(post);

                if (accomodation.getId() != null) {
                    accomodationRepository.save(accomodation);  // Lưu Accomodation (sử dụng save hoặc merge)
                }
                postRepository.save(post);
                return postMapper.toUpdatePostResponse(post);
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
                return postMapper.toPostDto(post.get());
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
            if (post.isEmpty()){
                throw new DataNotFoundException("Không tìm thấy post id " + idPost);
            }

            Optional<User> user = userRepository.findByEmail(usernameApprove);
            if(user.isPresent()){
                if (isApprove) {
                    post.get().setApproved(true);
                    post.get().setNotApproved(false);
                    actionService.createAction(post.get(), user.get(), ActionName.APPROVE);
//                applicationEventPublisher.publishEvent(new NotificationEvent(this, post.get()));
                } else {
                    post.get().setNotApproved(true);
                    post.get().setApproved(false);
                    actionService.createAction(post.get(), user.get(), ActionName.BLOCK);
//                applicationEventPublisher.publishEvent(new NotificationEvent(this, post.get()));
                }
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

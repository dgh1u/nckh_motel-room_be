package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.AccomodationDto;
import com.nckh.motelroom.dto.entity.CommentDto;
import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.entity.SearchDto;
import com.nckh.motelroom.dto.request.post.CreatePostRequest;
import com.nckh.motelroom.dto.request.post.UpdatePostRequest;
import com.nckh.motelroom.dto.response.post.CreatePostResponse;
import com.nckh.motelroom.dto.response.post.UpdatePostResponse;
import com.nckh.motelroom.exception.DataNotFoundException;
import com.nckh.motelroom.mapper.AccommodationMapper;
import com.nckh.motelroom.mapper.CommentMapper;
import com.nckh.motelroom.mapper.PostMapper;
import com.nckh.motelroom.mapper.UserMapper;
import com.nckh.motelroom.model.*;
import com.nckh.motelroom.model.enums.ActionName;
import com.nckh.motelroom.repository.*;
import com.nckh.motelroom.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.context.ApplicationEventPublisher;
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
@Slf4j
public class PostServiceImp implements PostService {
    //Inject Service
    private final ApplicationEventPublisher applicationEventPublisher;
    //Inject Repository into class
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final DistrictRepository districtRepository;

    private final AccomodationRepository accomodationRepository;

    private final CommentRepository commentRepository;

    private final ImageServiceImpl imageServiceImpl;

    private final ActionServiceImp actionService;

 ;   //Some Mapper in this
    private final PostMapper postMapper;

    private final AccommodationMapper accommodationMapper;

    private final UserMapper userMapper;

    private final CommentMapper commentMapper;

    @Override
    public Page<PostDto> getAllPost(Pageable page) {
        try {
            return postRepository.findAll(page).map(postMapper::toPostDto);
        }catch (Exception e){
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
        // Kiểm tra nếu page hợp lệ (nếu page < 0, trả về page 0)
        if (page < 0) {
            page = 0; // Điều chỉnh về trang đầu tiên nếu truyền page < 0
        }

        Optional<User> userOptional = userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            // Lấy danh sách bài đăng của người dùng với phân trang
            Page<Post> posts = postRepository.findByUser(userOptional.get(),
                    PageRequest.of(page, 12, Sort.by("createAt").descending()));

            // Chuyển đổi sang PostDto
            return posts.map(postMapper::toPostDto);
        } else {
            // Ném exception nếu không tìm thấy người dùng
            throw new DataNotFoundException("Không tìm thấy người dùng với id " + idUser);
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
        // Tìm bài viết
        Optional<Post> post = postRepository.findPostById(id);

        // Kiểm tra xem bài viết có tồn tại không
        if(post.isPresent()) {
            PostDto postDto = postMapper.toPostDto(post.get());

            //Lay cho o ra
            AccomodationDto accomodationDto = accommodationMapper.toAccomodationDto(post.get().getAccomodation());

            // Lấy các bình luận của bài đăng
            List<CommentDto> commentDtos = new ArrayList<>();
            List<Comment> comments = commentRepository.findCommentsByPostId(id);
            for (Comment comment : comments) {
                commentDtos.add(commentMapper.toCommentDto(comment));
            }

            // Lấy hình ảnh của bài đăng
            List<String> images = imageServiceImpl.getImagesByPost(id);

            // Thiết lập dữ liệu cho DTO
            postDto.setAccomodationDTO(accomodationDto);
            postDto.setImageStrings(images);
            postDto.setCommentDTOS(commentDtos);
            postDto.setUserDTO(userMapper.toUserDto(post.get().getUser()));
            postDto.getUserDTO().setB64(null);  // Loại bỏ b64 nếu không cần thiết

            // Trả về thông tin bài viết
            return postDto;
        } else {
            // Nếu không tìm thấy bài viết
            throw new DataNotFoundException("Không tìm thấy bài viết theo id đã cho");
        }
    }


//    @Override
//    @Transactional
//    public PostDto createPost(CreatePostRequest createPostRequest, String email) {
//        try{
//            Optional<User> user = userRepository.findByEmail(email);
//            if(user.isPresent()){
//                //create post
//                Post post = postMapper.createRequestDtoToPost(createPostRequest);
//                post.setCreateAt(LocalDateTime.now());
//                post.setLastUpdate(LocalDateTime.now());
//                post.setUser(user.get());
//                post.setDel(false);
//                post.setApproved(false);
//                post.setNotApproved(false);
//                //tao accomodation
//                Accomodation accomodation = accommodationMapper.toAccomodation(createPostRequest.getAccomodation());
//                accomodation.setPost(post);
//                // accômdation lưu tại đây
//                Accomodation accomodationSaved = accomodationRepository.save(accomodation);
//                post.setAccomodation(accomodationSaved);
//                post.setUser(user.get());
//                Post postSaved = postRepository.save(post);
//                actionService.createAction(post, user.get(), ActionName.CREATE);
//                return postMapper.toPostDto(postSaved);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return null;
//    }

    @Override
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest createPostRequest, String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (!user.isPresent()) {
                throw new DataNotFoundException("User not found with email: " + email);
            }

            // Tạo bài đăng
            Post post = postMapper.createRequestDtoToPost(createPostRequest);
            post.setCreateAt(LocalDateTime.now());
            post.setLastUpdate(LocalDateTime.now());
            post.setUser(user.get());
            post.setDel(false);  // Đánh dấu bài đăng không bị xóa
            post.setApproved(false);  // Bài đăng chưa được duyệt
            post.setNotApproved(false);  // Bài đăng chưa bị từ chối

            // Tạo đối tượng Accomodation
            Accomodation accomodation = accommodationMapper.toAccomodation(createPostRequest.getAccomodation());
            accomodation.setPost(post);

            // Lưu Accomodation
            Accomodation accomodationSaved = accomodationRepository.save(accomodation);
            post.setAccomodation(accomodationSaved);
            post.setUser(user.get());

            // Lưu bài đăng
            Post postSaved = postRepository.save(post);

            // Tạo action cho bài đăng
            actionService.createAction(post, user.get(), ActionName.CREATE);

            // Trả về response sau khi tạo bài đăng thành công
            return postMapper.toCreatePostResponse(postSaved);

        } catch (DataNotFoundException e) {
            throw e; // Re-throw if the user is not found
        } catch (Exception e) {
            // Log exception for debugging purposes
            log.error(e.getMessage());
            // Throw a general exception or custom exception
            throw new RuntimeException(e);
        }
    }



    @Override
    @Transactional
    public UpdatePostResponse updatePost(Long id, UpdatePostRequest updatePostRequest, String name) {
        try {
            // Lấy Post ra và xử lý
            Optional<Post> postOptional = postRepository.findPostById(id);
            // Tìm District từ request
            Optional<District> districtOptional = districtRepository.findDistrictById(updatePostRequest.getAccomodation().getIdDistrict());

            // Tạo đối tượng Accomodation từ request
            Accomodation accomodation = accommodationMapper.toAccomodation(updatePostRequest.getAccomodation());

            // Nếu không tìm thấy District, tạo mới District
            if (districtOptional.isEmpty()) {

                // Tạo District mới (có thể sử dụng thông tin từ request hoặc giá trị mặc định)
                District newDistrict = new District();
                newDistrict.setName("Default District"); // Ví dụ gán tên mặc định
                newDistrict.setXCoordinate(0.0); // Gán tọa độ mặc định
                newDistrict.setYCoordinate(0.0); // Gán tọa độ mặc định

                // Lưu District mới vào cơ sở dữ liệu
                newDistrict=districtRepository.save(newDistrict);

                // Gán District mới cho Accomodation
                accomodation.setDistrict(newDistrict);
                accomodation.setXCoordinate(newDistrict.getXCoordinate());
                accomodation.setYCoordinate(newDistrict.getYCoordinate());

            } else {
                // Nếu tìm thấy District, gán District cho Accomodation
                District district = districtOptional.get();
                accomodation.setDistrict(district);
                accomodation.setXCoordinate(district.getXCoordinate());
                accomodation.setYCoordinate(district.getYCoordinate());
            }

            // Kiểm tra nếu Post tồn tại
            if (postOptional.isPresent()) {
                Post post = postOptional.get();

                // Cập nhật thông tin bài đăng
                post.setTitle(updatePostRequest.getTitle());
                post.setContent(updatePostRequest.getContent());
                post.setDel(updatePostRequest.isDel());
                post.setLastUpdate(LocalDateTime.now());

                // Cập nhật thông tin Accomodation trong Post
                post.setAccomodation(accomodation);
                accomodation.setPost(post);

                // Lưu Accomodation và Post
                if (accomodation.getId() != null) {
                    accomodationRepository.save(accomodation);  // Lưu Accomodation (sử dụng save hoặc merge)
                }
                postRepository.save(post);

                return postMapper.toUpdatePostResponse(post);
            } else {
                throw new DataNotFoundException("Không tìm thấy bài đăng với ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi trong quá trình cập nhật bài đăng: "+ e.getMessage());  // Ném lỗi ra ngoài nếu có lỗi
        }
    }


    @Override
    public PostDto hidePost(Long id) {
        try {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) {
                post.get().setDel(true);  // Đánh dấu bài đăng là bị ẩn
                postRepository.save(post.get());
                return postMapper.toPostDto(post.get());  // Trả về PostDto đã bị ẩn
            } else {
                throw new DataNotFoundException("Không tìm thấy bài đăng với ID " + id);  // Nếu không tìm thấy bài đăng
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;  // Trả về null nếu có lỗi xảy ra
    }


    @Override
    public Page<PostDto> getMotelPost(boolean bool, int page, int sort) {
        try {
            // Khai báo Page<Post> để lưu kết quả
            Page<Post> postPage = null;

            // Kiểm tra điều kiện bool: true là lấy tin nhà trọ, false là lấy tin nhà nguyên căn
            boolean isMotel = bool;

            // Xử lý phân trang và sắp xếp
            Pageable pageable = PageRequest.of(page, 10);

            // Kiểm tra các loại sắp xếp
            switch (sort) {
                case 1: // Sắp xếp theo giá (ascending)
                    pageable = PageRequest.of(page, 10, Sort.by("accomodation.price").ascending());
                    break;
                case 2: // Sắp xếp theo giá (descending)
                    pageable = PageRequest.of(page, 10, Sort.by("accomodation.price").descending());
                    break;
                case 3: // Sắp xếp theo diện tích (ascending)
                    pageable = PageRequest.of(page, 10, Sort.by("accomodation.acreage").ascending());
                    break;
                case 4: // Sắp xếp theo diện tích (descending)
                    pageable = PageRequest.of(page, 10, Sort.by("accomodation.acreage").descending());
                    break;
                case 5: // Sắp xếp theo ngày tạo (descending)
                    pageable = PageRequest.of(page, 10, Sort.by("createAt").descending());
                    break;
                default:
                    // Nếu sort không hợp lệ, mặc định sẽ không sắp xếp
                    break;
            }

            // Lấy bài đăng từ repository
            if (isMotel) { // Nhà trọ
                postPage = postRepository.findAllByApprovedAndNotApprovedAndAccomodation_MotelAndDel(
                        true, false, true, pageable, false);
            } else { // Nhà nguyên căn
                postPage = postRepository.findAllByApprovedAndNotApprovedAndAccomodation_MotelAndDel(
                        true, false, false, pageable, false);
            }

            // Chuyển đổi sang PostDto và trả về
            return postPage.map(postMapper::toPostDto);
        } catch (Exception e) {
            log.error("Error while getting motel or house posts", e);
        }

        // Trả về null nếu có lỗi
        return null;
    }


    @Override
    public String deletePostByAdmin(Long id) {
        try {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) {
                postRepository.delete(post.get());
                return "Admin đã xóa post id " + id;  // Thông báo xóa thành công
            } else {
                throw new DataNotFoundException("Không tìm thấy post id " + id);  // Nếu không tìm thấy bài đăng
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;  // Trả về null nếu có lỗi xảy ra
    }

    @Override
    public String ApprovePost(Long idPost, String usernameApprove, boolean isApprove) {
        try {
            Optional<Post> post = postRepository.findById(idPost);
            if (post.isEmpty()) {
                return "Không tìm thấy post id " + idPost;  // Nếu không tìm thấy bài đăng
            }

            Optional<User> user = userRepository.findByEmail(usernameApprove);
            if (user.isPresent()) {
                if (isApprove) {
                    post.get().setApproved(true);
                    post.get().setNotApproved(false);
                    actionService.createAction(post.get(), user.get(), ActionName.APPROVE);
                } else {
                    post.get().setNotApproved(true);
                    post.get().setApproved(false);
                    actionService.createAction(post.get(), user.get(), ActionName.BLOCK);
                }
            }else{
                return "Không tìm thấy username " + usernameApprove;
            }
            postRepository.save(post.get());
            return "Bài đăng với ID " + idPost + " đã được " + (isApprove ? "duyệt" : "khóa") + " thành công";  // Thông báo kết quả
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;  // Trả về null nếu có lỗi xảy ra
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
//            log.error(e.getMessage());
//        }
        return null;
    }

    @Override
    public Page<PostDto> searchPostByMaps(SearchDto searchForm, int page, int sort) {
        return null;
    }

    @Override
    public Page<PostDto> getPostWaitingApprove(int page) {
        // Kiểm tra nếu page hợp lệ (nếu page < 0, trả về page 0)
        if (page < 0) {
            page = 0; // Điều chỉnh về trang đầu tiên nếu truyền page < 0
        }

        // Lấy danh sách bài đăng chờ duyệt từ repository với phân trang
        Page<Post> posts = postRepository.findByApprovedFalseAndNotApprovedFalse(
                PageRequest.of(page, 12, Sort.by("createAt").descending()));

        // Chuyển đổi từ Page<Post> thành Page<PostDto>
        return posts.map(postMapper::toPostDto);
    }
}

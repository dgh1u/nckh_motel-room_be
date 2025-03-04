package com.nckh.motelroom.controller;

import com.nckh.motelroom.config.JwtConfig;
import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.entity.SearchDto;
import com.nckh.motelroom.dto.request.post.CreatePostRequest;
import com.nckh.motelroom.dto.request.post.UpdatePostRequest;
import com.nckh.motelroom.dto.response.Response;
import com.nckh.motelroom.dto.response.post.CreatePostResponse;
import com.nckh.motelroom.dto.response.post.UpdatePostResponse;
import com.nckh.motelroom.exception.DataNotFoundException;
import com.nckh.motelroom.service.impl.PostServiceImp;
import com.nckh.motelroom.service.impl.UserDetailServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(value = "Tìm nhà trọ")
public class PostController {
    private final JwtConfig jwtConfig;

    private final UserDetailServiceImp userDetailServiceImp;

    private final PostServiceImp postService;

    @GetMapping("/post/hello-world")
    public String HelloWorld(){
        return "Hello World";
    }

    @ApiOperation(value = "Lấy danh sách tin đăng tìm kiếm theo tiêu chí")
    @GetMapping("/posts/search")
    public Page<PostDto> searchPost(SearchDto searchForm, @RequestParam int page, @RequestParam int sort){
        searchForm.setPriceStart(searchForm.getPriceStart()*1000000);
        searchForm.setPriceEnd(searchForm.getPriceEnd()*1000000);
        return postService.searchPost(searchForm, page, sort);
    }

    @ApiOperation(value = "Lấy danh sách tin đăng tìm kiếm xung quanh một vị trí")
    @GetMapping("/posts/searchbymaps")
    public Page<PostDto> searchPostMaps(SearchDto searchForm, @RequestParam int page, @RequestParam int sort){
        searchForm.setPriceStart(searchForm.getPriceStart()*1000000);
        searchForm.setPriceEnd(searchForm.getPriceEnd()*1000000);
        return postService.searchPostByMaps(searchForm, page, sort);
    }

    @ApiOperation(value = "Lấy tất cả tin đăng")
    @GetMapping("/posts")
    public Page<PostDto> getAllPost(@PageableDefault(page = 0, size = 12) Pageable  page) {
        return postService.getAllPost(page);
    }

    @ApiOperation(value = "Lấy danh sách tin đăng đã được duyệt")
    @GetMapping("/posts/approved/true")
    public Page<PostDto> getAllPostApproved(@RequestParam int page) {
        return postService.getPostByApproved(true, page);
    }

    @ApiOperation(value = "Lấy danh sách tin đăng đã bị khóa")
    @GetMapping("/posts/approved/false")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<PostDto> getAllPostNotApproved(@RequestParam int page) {
        return postService.getPostByApproved(false, page);
    }

    @ApiOperation(value = "Nếu bool = true lấy danh sách tin nhà trọ, ngược lại lấy danh sách tin nhà nguyên căn")
    @GetMapping("/posts/motel/{bool}")
    public ResponseEntity<Page<PostDto>> getMotelPost(
            @PathVariable boolean bool,
            @RequestParam int page,
            @RequestParam int sort) {
        try {
            // Lấy danh sách bài đăng theo loại (nhà trọ/nhà nguyên căn) và sắp xếp
            Page<PostDto> postDtos = postService.getMotelPost(bool, page, sort);

            // Trả về danh sách bài đăng
            return ResponseEntity.ok(postDtos);
        } catch (Exception e) {
            // Trả về 500 nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "Lấy danh sách tin đăng chờ duyệt")
    @GetMapping("/posts/waiting")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<PostDto>> getPostWaitingApprove(@RequestParam int page) {
        try {
            // Lấy danh sách tin đăng chờ duyệt từ service
            Page<PostDto> postDtos = postService.getPostWaitingApprove(page);
            return ResponseEntity.ok(postDtos); // Trả về 200 OK nếu thành công
        } catch (Exception e) {
            // Trả về 500 nếu có lỗi server
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "Lấy danh sách tin đăng của một người dùng")
    @GetMapping("/post/user/{idUser}")
    public ResponseEntity<Page<PostDto>> getPostByIdUser(@PathVariable long idUser,
                                                         @RequestParam int page) {
        try {
            // Lấy danh sách tin đăng
            Page<PostDto> postDtos = postService.getPostByIdUser(idUser, page);
            return ResponseEntity.ok(postDtos); // Trả về 200 OK nếu thành công
        } catch (DataNotFoundException e) {
            // Trả về 404 nếu không tìm thấy người dùng
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Trả về 500 nếu có lỗi server
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @ApiOperation(value = "Lấy thông tin của một tin đăng")
    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        try {
            PostDto postDto = postService.getPostById(id);
            return ResponseEntity.ok(postDto);  // Trả về HTTP 200 OK nếu thành công
        } catch (DataNotFoundException e) {
            // Trả về HTTP 404 nếu không tìm thấy bài viết
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Trả về HTTP 500 nếu có lỗi server
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Đăng tin mới")
    @PostMapping("/post")
    public ResponseEntity<Response<CreatePostResponse>> createPost(@RequestHeader("Authorization") String token, @RequestBody @Valid CreatePostRequest createPostRequest) {
        String userId = jwtConfig.getUserIdFromJWT(token.split(" ")[1]);
        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(userId);

        try {
            CreatePostResponse createdPost = postService.createPost(createPostRequest, userDetails.getUsername());

            // Trả về response nếu tạo bài đăng thành công
            return ResponseEntity.ok(new Response<>("Tạo bài đăng thành công", createdPost, HttpStatus.OK.value()));

        } catch (DataNotFoundException e) {
            // Trả về lỗi nếu không tìm thấy người dùng
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>(e.getMessage(), null, HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            // Trả về lỗi server nếu có sự cố bất ngờ
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        } catch (Exception e) {
            // Trả về lỗi chung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("Lỗi không xác định: " + e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }


    @ApiOperation(value = "Duyệt/Khóa tin đăng")
    @PutMapping("/post/{id}/approve/{bool}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> approvePostAndLogging(@RequestHeader("Authorization") String token,
                                                        @PathVariable Long id,
                                                        @PathVariable boolean bool) {
        String userId = jwtConfig.getUserIdFromJWT(token.split(" ")[1]);
        System.out.println("No");
        String resultMessage = postService.ApprovePost(id, userId, bool);
        System.out.println("No");

        if (resultMessage == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy bài đăng với ID " + id);  // Trả về status 404 nếu không tìm thấy bài đăng
        }

        return ResponseEntity.ok(resultMessage);  // Trả về status 200 nếu duyệt hoặc khóa thành công
    }

    // ok
    @ApiOperation(value = "Cập nhật một tin đăng")
        @PutMapping("/post/{id}")
        public ResponseEntity<UpdatePostResponse> updatePost(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody UpdatePostRequest updatePostRequest) {
            String userId = jwtConfig.getUserIdFromJWT(token.split(" ")[1]);
            UpdatePostResponse updatedPost = postService.updatePost(id, updatePostRequest, userId);
            return ResponseEntity.ok(updatedPost);
    }

    @ApiOperation(value = "Ẩn một tin đăng")
    @PutMapping("/post/hide/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> hidePost(@PathVariable Long id) {
        PostDto postDto = postService.hidePost(id);

        if (postDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy bài đăng với ID " + id);  // Trả về status 404 nếu không tìm thấy bài đăng
        }

        return ResponseEntity.ok("Bài đăng với ID " + id + " đã được ẩn thành công");  // Trả về status 200 nếu ẩn thành công
    }

    @ApiOperation(value = "Xóa một tin đăng")
    @DeleteMapping("/post/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deletePostByAdmin(@PathVariable Long id) {
        String message = postService.deletePostByAdmin(id);

        if (message == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy bài đăng với ID " + id);  // Trả về status 404 nếu không tìm thấy bài đăng
        }

        return ResponseEntity.ok(message);  // Trả về status 200 nếu xóa thành công
    }

}

package com.nckh.motelroom.repository;

import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.User;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findByUser(User user, Pageable pageable);

    Optional<Post> findPostById(Long id);

    Page<Post> findAllByApprovedAndNotApprovedAndDel(boolean approved, boolean notApproved, boolean del, Pageable pageable);

    Page<Post> findAllByApprovedAndNotApproved(boolean approved, boolean notApproved, Pageable pageable);

    Page<Post> findAllByUser_EmailAndDelAndApproved(String email, boolean del, boolean approved, Pageable page);
    // Phương thức truy vấn các bài đăng chưa duyệt
    Page<Post> findByApprovedFalseAndNotApprovedFalse(Pageable pageable);
    // Phương thức tìm bài đăng nhà nguyên căn với các điều kiện lọc
    Page<Post> findAllByApprovedAndNotApprovedAndAccomodation_MotelAndDel(boolean approved, boolean notApproved, boolean motel, Pageable pageable, boolean del);

}

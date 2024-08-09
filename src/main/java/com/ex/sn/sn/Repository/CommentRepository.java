package com.ex.sn.sn.Repository;

import com.ex.sn.sn.Entity.Comment;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByPost(Post post);
    Comment findOneById(Long id);

    Comment findByUserAndId(User user, Long Id);
    Page<Comment> findByPost(Post post, Pageable pageable);

    @Query(value = "select count(*) from comment c where c.user_id = ?1 and c.created_date between ?2 and ?3", nativeQuery = true)
    Long countComment(Long userInfoId, Date from, Date to);

}


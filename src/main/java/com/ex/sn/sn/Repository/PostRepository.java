package com.ex.sn.sn.Repository;

import com.ex.sn.sn.Entity.Comment;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByUserAndId(User user, Long id);
    Post findOneById(Long id);
    Post findByComments(Comment comment);
    Page<Post> findByContentContains(String content, Pageable pageable);
    Page<Post> findByUser(User user, Pageable pageable);
    @Query(value = "select p.* from friendships fr join user u on u.id=fr.address_user_id join post p on p.user_id = u.id and fr.address_user_id=?1 order by p.created_at desc ",
            countQuery = "select count(*) from friendships fr join user u on u.id=fr.address_user_id join post p on p.user_id = u.id and fr.address_user_id=?1",
            nativeQuery = true)
    Page<Post> findByUserId(Long userId, Pageable pageable);
    @Query(value = "select count(*) from post p where p.user_id = ?1 and p.created_at between ?2 and ?3",
            nativeQuery = true)
    long countPosts(Long userId, LocalDate from, LocalDate to);

}

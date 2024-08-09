package com.ex.sn.sn.Repository;

import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByUserAndId(User user, Long id);
    Post findOneById(Long id);
    Page<Post> findByUser(User user, Pageable pageable);
    @Query(value = "select p.* from friend fr join user_info_list_friend u_fr on fr.id=u_fr.list_friend_id join post p on p.user_info_id = u_fr.user_info_id and fr.user_info_id=?1 order by p.created_date desc ",
            countQuery = "select count(*) from friend fr join user_info_list_friend u_fr on fr.id=u_fr.list_friend_id join post p on p.user_info_id = u_fr.user_info_id and fr.user_info_id=?1",
            nativeQuery = true)
    Page<Post> findByUserId(Long userId, Pageable pageable);
    @Query(value = "select count(*) from post p where p.user_info_id = ?1 and p.created_date between ?2 and ?3",
            nativeQuery = true)
    long countPosts(Long userInfoId, Date from, Date to);

}

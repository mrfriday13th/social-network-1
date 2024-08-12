package com.ex.sn.sn.Repository;

import com.ex.sn.sn.Entity.Comment;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Entity.Reaction;
import com.ex.sn.sn.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Long countByPost(Post post);

    Reaction findOneByPostAndUser(Post post, User user);

    Reaction findOneByCommentAndUser(Comment comment, User user);
}

package com.ex.sn.sn.Service.Impl;

import com.ex.sn.sn.DTO.Comment.LikeCommentReqDto;
import com.ex.sn.sn.DTO.Post.LikePostReqDto;
import com.ex.sn.sn.Entity.Comment;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Entity.Reaction;
import com.ex.sn.sn.Entity.User;
import com.ex.sn.sn.Exception.AppException;
import com.ex.sn.sn.Exception.ErrorCode;
import com.ex.sn.sn.Repository.CommentRepository;
import com.ex.sn.sn.Repository.PostRepository;
import com.ex.sn.sn.Repository.ReactionRepository;
import com.ex.sn.sn.Repository.UserRepository;
import com.ex.sn.sn.Service.PostService;
import com.ex.sn.sn.Service.ReactionService;
import com.ex.sn.sn.Utils.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostService  postService;
    private final CommentRepository commentRepository;


    @Override
    public  void likePost(LikePostReqDto reqDto){
        Post post  = postRepository.findOneById(reqDto.getId());
        if (post == null) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }

        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
        // if post not exist or post that only me of other user then can not like
        if (!postService.checkRightAccessPost(post, user)) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }

        // check if have liked post before
        Reaction reaction = reactionRepository.findOneByPostAndUser(post, user);
        if (reaction == null) {
            Reaction newReaction = new Reaction();
            newReaction.setUser(user);
            newReaction.setPost(post);
            reactionRepository.save(newReaction);
        } else {
            // if like then dislike, if dislike then like
            reactionRepository.delete(reaction);
        }
    }

    @Override
    public void likeComment(LikeCommentReqDto reqDto) {
        Comment comment = commentRepository.findOneById(reqDto.getId());
        if (comment == null) {
            throw new AppException(ErrorCode.COMMENT_NOTEXISTED);
        }

        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

        Post post = postRepository.findByComments(comment);

        if (!postService.checkRightAccessPost(post, user)) {
            throw new AppException(ErrorCode.POST_NOTEXISTED);
        }

        // check if have liked post before
        Reaction reaction = reactionRepository.findOneByPostAndUser(post, user);
        if (reaction == null) {
            Reaction newReaction = new Reaction();
            newReaction.setUser(user);
            newReaction.setPost(post);
            reactionRepository.save(newReaction);
        } else {
            // if like then dislike, if dislike then like
            reactionRepository.delete(reaction);
        }
    }

}

package com.ex.sn.sn.Service;

import com.ex.sn.sn.DTO.Comment.LikeCommentReqDto;
import com.ex.sn.sn.DTO.Post.LikePostReqDto;

public interface ReactionService {

    void likePost(LikePostReqDto reqDto);

    void likeComment(LikeCommentReqDto reqDto);

}

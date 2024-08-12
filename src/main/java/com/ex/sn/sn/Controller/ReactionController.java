package com.ex.sn.sn.Controller;

import com.ex.sn.sn.DTO.Comment.LikeCommentReqDto;
import com.ex.sn.sn.DTO.Post.LikePostReqDto;
import com.ex.sn.sn.Response.BaseResponse;
import com.ex.sn.sn.Service.ReactionService;
import com.ex.sn.sn.Utils.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    /**
     * like or dislike post
     *
     * @param postId
     */
    @PostMapping("/post")
    @Operation(summary = "API like post", description = "like or dislike a post")
    @ApiResponse(responseCode = "200", description = "like or dislike post successfully")
    @ApiResponse(responseCode = "400", description = "like or dislike post error")
    public BaseResponse<?> likePost(@Valid @RequestBody LikePostReqDto reqDto) {
        reactionService.likePost(reqDto);
        return BaseResponse.builder().message(CommonConstants.LIKE_PROCESS).build();
    }

    /**
     * like or dislike comment
     *
     * @param commentId
     */
    @PostMapping("/comment")
    @Operation(summary = "API like comment", description = "like or dislike a comment")
    @ApiResponse(responseCode = "200", description = "like or dislike comment successfully")
    @ApiResponse(responseCode = "400", description = "like or dislike comment error")
    public BaseResponse<?> likeComment(@Valid @RequestBody LikeCommentReqDto reqDto) {
        reactionService.likeComment(reqDto);
        return BaseResponse.builder().message(CommonConstants.LIKE_PROCESS).build();
    }

}

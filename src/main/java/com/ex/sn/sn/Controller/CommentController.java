package com.ex.sn.sn.Controller;

import com.ex.sn.sn.DTO.Comment.CommentListResDto;
import com.ex.sn.sn.DTO.Comment.CommentReqPostDto;
import com.ex.sn.sn.DTO.Comment.CommentReqPutDto;
import com.ex.sn.sn.DTO.Comment.CommentResDto;
import com.ex.sn.sn.Response.BaseResponse;
import com.ex.sn.sn.Service.CommentService;
import com.ex.sn.sn.Utils.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    /**
     * create comment
     *
     * @param reqDto {@link CommentReqPostDto}
     * @param idPost
     * @return {@link CommentResDto}
     */
    @PostMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "API comment")
    @ApiResponse(responseCode = "200", description = "create comment successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResDto.class)))
    @ApiResponse(responseCode = "400", description = "create comment error")
    @ApiResponse(responseCode = "404", description = "Post not found")
    @ApiResponse(responseCode = "401", description = "UserName not found")
    public BaseResponse <CommentResDto> createComment(@PathVariable("id") Long postId,
                                               @RequestPart @Valid CommentReqPostDto reqDto, @RequestPart(required = false) MultipartFile multipartFile) {
        CommentResDto resDto = commentService.createComment(postId, reqDto, multipartFile);
        return BaseResponse.<CommentResDto>builder().result(resDto).message(CommonConstants.COMMENT_CREATE_SUCCESS)
                .build();
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "API update comment", description = "Update a comment")
    @ApiResponse(responseCode = "200", description = "Update comment successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResDto.class)))
    @ApiResponse(responseCode = "400", description = "Update comment error")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @ApiResponse(responseCode = "401", description = "UserName not found")
    public BaseResponse<CommentResDto> updateComment(@PathVariable("id") Long commentId,
                                                     @RequestPart @Valid CommentReqPutDto reqDto, @RequestPart(required = false) MultipartFile multipartFile) {
        CommentResDto resDto = commentService.updateComment(commentId, reqDto, multipartFile);
        return BaseResponse.<CommentResDto>builder().result(resDto).message(CommonConstants.COMMENT_UPDATE_SUCCESS)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "API delete comment", description = "Delete a comment")
    @ApiResponse(responseCode = "200", description = "Delete comment successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @ApiResponse(responseCode = "401", description = "UserName not found")
    public BaseResponse<?> deleteComment(@PathVariable("id") Long idComment) {
        commentService.delete(idComment);
        return BaseResponse.builder().message(CommonConstants.COMMENT_DELETE_SUCCESS).build();
    }

    @GetMapping("all/{id}")
    @Operation(summary = "API get all comment of a post", description = "get list comment")
    @ApiResponse(responseCode = "200", description = "get comment successfully")
    @ApiResponse(responseCode = "400", description = "get comment error")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @ApiResponse(responseCode = "401", description = "UserName not found")
    public BaseResponse<CommentListResDto> getAllComment(@PathVariable("id") Long id,
                                                         @RequestParam Integer pageNumber) {
        Page<CommentResDto> result = commentService.getAllComment(id, pageNumber);
        CommentListResDto res = new CommentListResDto();
        res.setListComment(result);
        return BaseResponse.<CommentListResDto>builder().result(res).message(CommonConstants.USER_SEARCH_SUCCES)
                .build();
    }
}

package com.ex.sn.sn.Service;

import com.ex.sn.sn.DTO.Comment.CommentReqPostDto;
import com.ex.sn.sn.DTO.Comment.CommentReqPutDto;
import com.ex.sn.sn.DTO.Comment.CommentResDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface CommentService {
    CommentResDto createComment(Long postId, CommentReqPostDto reqDto, MultipartFile files);

    CommentResDto updateComment(Long commentId, CommentReqPutDto reqDto, MultipartFile files);

    void delete(Long id);

    Page<CommentResDto> getAllComment(Long id, Integer pageNo);
}

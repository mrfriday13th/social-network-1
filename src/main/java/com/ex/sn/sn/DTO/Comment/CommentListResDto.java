package com.ex.sn.sn.DTO.Comment;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentListResDto {

    private Page<CommentResDto> listComment;

}

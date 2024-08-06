package com.ex.sn.sn.DTO.Post;

import java.util.List;

import com.ex.sn.sn.Entity.Post;
import org.hibernate.validator.constraints.Length;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostPutReqDto {

    @Length(max = 500, message = "must be smaller than 500 characters!")
    private String content;

    private List<Long> listImageIdDeletes;

    private Post.Status status = Post.Status.PUBLIC;
}
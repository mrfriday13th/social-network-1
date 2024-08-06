package com.ex.sn.sn.DTO.Post;

import com.ex.sn.sn.Entity.Post;
import org.hibernate.validator.constraints.Length;

//import com.example.social_network.enumdef.PostType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostPostReqDto {

    @Length( max = 500, message = "must be smaller than 500 characters!")
    private String content;

    private Post.Status status = Post.Status.PUBLIC;
}
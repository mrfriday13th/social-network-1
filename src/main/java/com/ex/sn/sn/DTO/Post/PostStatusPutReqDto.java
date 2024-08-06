package com.ex.sn.sn.DTO.Post;

import com.ex.sn.sn.Entity.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostStatusPutReqDto {

    private Post.Status status;
}

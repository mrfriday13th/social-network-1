package com.ex.sn.sn.DTO.Utils.Post;

import com.ex.sn.sn.DTO.Post.PostPostResDto;
import com.ex.sn.sn.DTO.Utils.ResponseUtilsAdapter;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Mapper.Post.PostResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostResponseUtils extends ResponseUtilsAdapter<Post, PostPostResDto> {
    private final PostResponseMapper postResponseMapper;





}

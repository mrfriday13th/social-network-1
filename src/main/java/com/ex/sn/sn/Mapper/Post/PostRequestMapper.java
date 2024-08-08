package com.ex.sn.sn.Mapper.Post;

import com.ex.sn.sn.DTO.Post.PostPostResDto;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Mapper.MapperI;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostRequestMapper extends MapperI<Post, PostPostResDto> {
}

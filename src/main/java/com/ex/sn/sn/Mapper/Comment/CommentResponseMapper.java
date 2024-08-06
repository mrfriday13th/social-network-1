package com.ex.sn.sn.Mapper.Comment;

import com.ex.sn.sn.DTO.Comment.CommentResDto;
import com.ex.sn.sn.Entity.Comment;
import com.ex.sn.sn.Mapper.MapperI;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentResponseMapper extends MapperI<Comment, CommentResDto> {

}

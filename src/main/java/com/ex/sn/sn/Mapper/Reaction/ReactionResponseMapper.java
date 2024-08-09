package com.ex.sn.sn.Mapper.Reaction;

import com.ex.sn.sn.DTO.Reaction.ReactionResDto;
import com.ex.sn.sn.Entity.Reaction;
import com.ex.sn.sn.Mapper.MapperI;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ReactionResponseMapper extends MapperI<Reaction, ReactionResDto> {
}

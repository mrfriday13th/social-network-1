package com.ex.sn.sn.Mapper.AvatarUser;

import com.ex.sn.sn.Entity.AvatarUser;
import com.ex.sn.sn.Mapper.MapperI;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvatarUserResponseMapper extends MapperI<AvatarUser, AvatarGetResDto> {
}

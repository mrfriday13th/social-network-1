package com.ex.sn.sn.Mapper.User;

import com.ex.sn.sn.DTO.User.UserResDto;
import com.ex.sn.sn.Entity.User;
import com.ex.sn.sn.Mapper.MapperI;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends MapperI<User, UserResDto> {
}

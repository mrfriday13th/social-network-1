package com.ex.sn.sn.Mapper.InforUser;

import com.ex.sn.sn.DTO.InforUser.InforUserResDto;
import com.ex.sn.sn.Entity.InforUser;
import com.ex.sn.sn.Mapper.MapperI;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InforUserResponseMapper extends MapperI<InforUser, InforUserResDto> {
}

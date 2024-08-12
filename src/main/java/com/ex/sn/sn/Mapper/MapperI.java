package com.ex.sn.sn.Mapper;

import com.ex.sn.sn.DTO.Post.PostPostReqDto;

import java.util.List;
import java.util.Set;

public interface MapperI <S,D>{
    S dtoToEntity(PostPostReqDto obj);

    D entityToDto(S obj);

    List<S> entityListtoDtoList(List<D> dtos);

    List<D> dtoListToEntityList(List<S> entities);

    Set<S> entityListtoDtoList(Set<D> dtos);

    Set<D> dtoListToEntityList(Set<S> entities);
}

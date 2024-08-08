package com.ex.sn.sn.DTO.Utils;

import java.util.List;

public interface ResponseUtils <S, D>{
    D convert(S obj);

    List<D> convert(List<S> obj);

}

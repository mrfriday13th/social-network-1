package com.ex.sn.sn.Service;

import com.ex.sn.sn.DTO.Post.PostPostResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TimeLineService {
    Page<PostPostResDto> getTimeLinePost(Pageable page);
}

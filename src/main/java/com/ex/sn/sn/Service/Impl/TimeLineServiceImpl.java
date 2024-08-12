package com.ex.sn.sn.Service.Impl;

import com.ex.sn.sn.DTO.Post.PostPostResDto;
import com.ex.sn.sn.DTO.Utils.Post.PostResponseUtils;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Entity.User;
import com.ex.sn.sn.Repository.PostRepository;
import com.ex.sn.sn.Repository.UserRepository;
import com.ex.sn.sn.Service.TimeLineService;
import com.ex.sn.sn.Utils.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeLineServiceImpl implements TimeLineService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostResponseUtils postResponseUtils;
    @Override
    public Page<PostPostResDto> getTimeLinePost(Pageable page) {

        User user = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

        Page<Post> pagedResult = postRepository.findByUser(user, page);

        List<PostPostResDto> postResponseList = pagedResult.stream().filter(
                        post -> post.getUser().getId() == user.getId() || (post.getUser().getId() != user.getId()
                                && post.getStatus() != Post.Status.PRIVATE))
                .map(postResponseUtils::convert).collect(Collectors.toList());
        return new PageImpl<>(postResponseList, page, postResponseList.size());
    }
}

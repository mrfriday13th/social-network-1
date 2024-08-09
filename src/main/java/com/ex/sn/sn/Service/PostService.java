package com.ex.sn.sn.Service;

import com.ex.sn.sn.DTO.Post.*;
import com.ex.sn.sn.Entity.Post;
import com.ex.sn.sn.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PostPostResDto createPost(PostPostReqDto reqDto, MultipartFile[] files);
    PostPostResDto update(Long id, PostPutReqDto reqDto, MultipartFile[] files);
    DeletePostResDto delete(Long id);
    PostPostResDto getPostDetail(Long Id);
    Page<PostPostResDto> getUserAllPost(Long id, Integer pageNo);
    Page<PostPostResDto> getAllPostByKeyword(Integer pageNo, String searchContent);
    PostPostResDto updatePrivacy(Long postId, PostStatusPutReqDto reqDto);
    boolean checkRightAccessPost(Post post, User user);
}

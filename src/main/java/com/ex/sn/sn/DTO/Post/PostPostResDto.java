package com.ex.sn.sn.DTO.Post;

import com.ex.sn.sn.DTO.AbstractDto;
import com.ex.sn.sn.DTO.Comment.CommentResDto;
import com.ex.sn.sn.DTO.FileUpload.FileResDto;
import com.ex.sn.sn.DTO.Reaction.ReactionResDto;
import com.ex.sn.sn.DTO.User.UserResDto;
import com.ex.sn.sn.Entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class PostPostResDto extends AbstractDto {
    private Long id;
    private String content;
    private Post.Status status;
    private List<FileResDto> files;
    private List<ReactionResDto> likes;
    private List<CommentResDto> comments;
    private Long likeCount;
    private Long commentCount;
    private UserResDto user;
}

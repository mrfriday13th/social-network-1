package com.ex.sn.sn.DTO.Comment;

import com.ex.sn.sn.DTO.AbstractDto;
import com.ex.sn.sn.DTO.FileUpload.FileResDto;
import com.ex.sn.sn.DTO.Reaction.ReactionResDto;
import com.ex.sn.sn.DTO.User.UserResDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDto extends AbstractDto {
    private String content;

    private FileResDto file;

    private UserResDto user;

    private List<ReactionResDto> reactions;
}

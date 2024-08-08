package com.ex.sn.sn.DTO.Utils.Comment;

import com.ex.sn.sn.DTO.Comment.CommentResDto;
import com.ex.sn.sn.DTO.Utils.Reaction.ReactionResponseUtils;
import com.ex.sn.sn.DTO.Utils.ResponseUtilsAdapter;
import com.ex.sn.sn.DTO.Utils.User.UserResponseUtils;
import com.ex.sn.sn.Entity.Comment;
import com.ex.sn.sn.Mapper.Comment.CommentResponseMapper;
import com.ex.sn.sn.Mapper.FileUpload.FileUploadResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CommentResponseUtils extends ResponseUtilsAdapter<Comment, CommentResDto> {

    private final FileUploadResponseMapper fileMapper;

    private final CommentResponseMapper commentMapper;

    private final UserResponseUtils userResponseUtils;

    private final ReactionResponseUtils reactionResponseUtils;

    @Override
    public CommentResDto convert(Comment entity) {
        CommentResDto resDto = commentMapper.entityToDto(entity);
        resDto.setContent(entity.getContent());
        resDto.setFile(fileMapper.entityToDto(entity.getFileUpload()));
        resDto.setReactions(reactionResponseUtils.convert(entity.getReactions()));
        resDto.setUser(userResponseUtils.convert(entity.getUser()));
        return resDto;
    }

    @Override
    public List<CommentResDto> convert(List<Comment> listEntity) {
        List<CommentResDto> listResDto = new ArrayList<>();
        listEntity.stream().forEach(entity -> {
            CommentResDto res = commentMapper.entityToDto(entity);
            res.setContent(entity.getContent());
            res.setFile(fileMapper.entityToDto(entity.getFileUpload()));
            res.setReactions(reactionResponseUtils.convert(entity.getReactions()));
            res.setUser(userResponseUtils.convert(entity.getUser()));
            listResDto.add(res);
        });
        return listResDto;
    }

}

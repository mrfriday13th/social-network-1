package com.ex.sn.sn.DTO.Utils.Reaction;

import com.ex.sn.sn.DTO.Reaction.ReactionResDto;
import com.ex.sn.sn.DTO.Utils.ResponseUtilsAdapter;
import com.ex.sn.sn.DTO.Utils.User.UserResponseUtils;
import com.ex.sn.sn.Entity.Reaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@AllArgsConstructor
public class ReactionResponseUtils extends ResponseUtilsAdapter<Reaction, ReactionResDto> {
    private final UserResponseUtils userResponseUtils;

    @Override
    public ReactionResDto convert(Reaction obj) {
        return ReactionResDto.builder().user(userResponseUtils.convert(obj.getUser())).build();
    }

    @Override
    public List<ReactionResDto> convert(List<Reaction> obj) {
        List<ReactionResDto> userResponseList = new ArrayList<>();
        obj.stream().forEach(item -> {
            userResponseList
                    .add(ReactionResDto.builder().user(userResponseUtils.convert(item.getUser())).build());
        });
        return userResponseList;
    }
}

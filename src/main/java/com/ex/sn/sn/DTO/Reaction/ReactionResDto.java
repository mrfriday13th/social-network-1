package com.ex.sn.sn.DTO.Reaction;

import com.ex.sn.sn.DTO.User.UserResDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionResDto {
    @JsonProperty("user")
    private UserResDto user;
}

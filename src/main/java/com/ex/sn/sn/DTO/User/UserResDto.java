package com.ex.sn.sn.DTO.User;

import com.ex.sn.sn.DTO.AbstractDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResDto extends AbstractDto {
    private Long id;

    private String username;

    private String cunrrentAvatarId;

    private String urlImage;

    private String lastName;

    private String firstName;

    private boolean gender;

}

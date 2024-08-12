package com.ex.sn.sn.DTO.AvatarUser;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
@Getter
@Setter
public class AvatarUserListResDto {
    private Page<AvatarUserResDto> listAvatarUserRes;
}

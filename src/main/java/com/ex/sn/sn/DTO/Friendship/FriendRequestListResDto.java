package com.ex.sn.sn.DTO.Friendship;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FriendRequestListResDto {
    private Page<FriendRequestResDto> listFriendRequestResDto;
}

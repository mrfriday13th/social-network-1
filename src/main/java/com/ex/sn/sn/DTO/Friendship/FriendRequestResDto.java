package com.ex.sn.sn.DTO.Friendship;

import com.ex.sn.sn.DTO.User.UserResDto;

import java.time.LocalDateTime;

public class FriendRequestResDto {

    private LocalDateTime createdAt;

    private UserResDto user;

    public UserResDto getUser() {
        return user;
    }

    public void setUser(UserResDto userInfo) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

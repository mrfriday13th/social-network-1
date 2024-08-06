package com.ex.sn.sn.DTO.Friendship;

import java.time.LocalDateTime;
import java.util.Date;

import com.ex.sn.sn.DTO.User.UserResDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FriendResDto {
    private UserResDto user;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public UserResDto getUser() {
        return user;
    }

    public void setUser(UserResDto user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


package com.ex.sn.sn.DTO.Comment;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LikeCommentReqDto {
    @NotNull(message = "is required")
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package com.ex.sn.sn.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDto {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

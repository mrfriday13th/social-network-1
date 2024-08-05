package com.ex.sn.sn.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse <T> {
    @Builder.Default
    private int code = 200;
    private String message;
    private T result;
}

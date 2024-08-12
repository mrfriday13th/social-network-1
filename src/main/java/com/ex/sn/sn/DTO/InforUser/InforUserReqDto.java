package com.ex.sn.sn.DTO.InforUser;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InforUserReqDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private boolean gender;
    private String user_id;
}
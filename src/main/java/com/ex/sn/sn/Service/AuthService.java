package com.ex.sn.sn.Service;

import com.ex.sn.sn.DTO.Auth.*;
import com.ex.sn.sn.DTO.User.UserSignupResDto;

public interface AuthService {
    public UserSignupResDto insertUser(RegistUserRepDto reqDto);

    public PasswordResetRequestResDto requestPasswordReset(PasswordResetRequestReqDto reqDto);

    public void resetNewPassword(PasswordResetReqDto reqDto);

    public OtpResDto generateOTP(OtpReqDto reqDto);

    public OtpGetResDto validateOTP(OtpGetReqDto reqDto);
}

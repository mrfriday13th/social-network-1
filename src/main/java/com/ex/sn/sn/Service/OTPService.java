package com.ex.sn.sn.Service;

import org.springframework.stereotype.Component;

//@Component
public interface OTPService {

    public int generateOTP(String email);

    public int getOtp(String email);

    public void clearOTP(String key);
}

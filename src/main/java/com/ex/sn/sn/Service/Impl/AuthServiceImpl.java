package com.ex.sn.sn.Service.Impl;

import com.ex.sn.sn.DTO.Auth.*;
import com.ex.sn.sn.DTO.User.UserSigupResDto;
import com.ex.sn.sn.Entity.PasswordResetToken;
import com.ex.sn.sn.Entity.User;
import com.ex.sn.sn.Exception.AppException;
import com.ex.sn.sn.Exception.ErrorCode;
import com.ex.sn.sn.JWT.JwtUtils;
import com.ex.sn.sn.JWT.SecurityConstant;
import com.ex.sn.sn.Repository.PasswordResetTokenReponsitory;
import com.ex.sn.sn.Repository.UserRepository;
import com.ex.sn.sn.Service.AuthService;
import com.ex.sn.sn.Service.OTPService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private  final PasswordResetTokenReponsitory passwordResetTokenReponsitory;
    private  final JwtUtils jwtUtils;
    private  final AuthenticationManager authenticationManager;
    private  final ModelMapper modelMapper;
    private  final OTPService otpService;


    @Override
    public UserSigupResDto insertUser(RegistUserRepDto reqDto){
        //ma hoa password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        reqDto.setPassword(encoder.encode(reqDto.getPassword()));
        User user = modelMapper.map(reqDto, User.class);
        user.setRole("User");

        //check user ton tai
        Optional<User> existUser = userRepository.findByUsername(reqDto.getUsername());
        if(existUser.isPresent()){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        userRepository.save(user);
        return modelMapper.map(user, UserSigupResDto.class);
    }

    @Override
    public PasswordResetRequestResDto requestPasswordReset(PasswordResetRequestReqDto reqDto){
        Optional<User> userEntity = userRepository.findByUsername(reqDto.getUsername());

        if (!userEntity.isPresent()){
            throw new AppException(ErrorCode.SIGNIN_ERROR);
        }
        // tao token
        String token = jwtUtils.generateToken(userEntity.get().getUsername(), SecurityConstant.PASSWORD_RESET_EXPIRATION_TIME);
        PasswordResetToken passwordResetTokenEntity = new PasswordResetToken();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUser(userEntity.get());
        passwordResetTokenReponsitory.save(passwordResetTokenEntity);
        // tao link reset password
        PasswordResetRequestResDto resDto = new PasswordResetRequestResDto();
        resDto.setLinkResetPassword(SecurityConstant.URL_AUTH + token);

        return resDto;
    }
    @Override
    public void resetNewPassword(PasswordResetReqDto reqDto) {
        // kiem tra neu token sai
        if (!jwtUtils.validateJwtToken(reqDto.getToken())) {
            throw new AppException(ErrorCode.TOKEN_RESET_PSW_WRONG);
        }

        PasswordResetToken passwordResetTokenEntity = passwordResetTokenReponsitory.findByToken(reqDto.getToken());
        // kiem tra neu token rong
        if (passwordResetTokenEntity == null) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = passwordResetTokenEntity.getUser();

        user.setPassword(encoder.encode(reqDto.getPassword()));
        User savedUser = userRepository.save(user);

        if (savedUser == null) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        passwordResetTokenReponsitory.delete(passwordResetTokenEntity);
    }

    @Override
    public OtpResDto generaterOpt(OtpReqDto reqDto) {
        OtpResDto restDto = new OtpResDto();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(reqDto.getUserName(), reqDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.isAuthenticated()) {
                int otp = otpService.generateOTP(reqDto.getUserName());
                restDto.setOtp(otp);
                return restDto;
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.SIGNIN_ERROR);
        }
        return restDto;
    }
    @Override
    public OtpGetResDto validateOtp(OtpGetReqDto reqDto) {
        OtpGetResDto resDto = new OtpGetResDto();
        int serverOtp = otpService.getOtp(reqDto.getUserName());
        if (serverOtp > 0) {
            if (Integer.valueOf(reqDto.getOtp()) == serverOtp) {
                otpService.clearOTP(reqDto.getUserName());
                resDto.setToken(jwtUtils.generateToken(reqDto.getUserName(), SecurityConstant.EXPIRATION_TIME));
                return resDto;
            } else {
                throw new AppException(ErrorCode.OTP_INVALID);
            }
        } else {
            throw new AppException(ErrorCode.OTP_INVALID);
        }
    }
}

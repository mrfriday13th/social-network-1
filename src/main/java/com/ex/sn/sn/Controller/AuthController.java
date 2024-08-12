package com.ex.sn.sn.Controller;

import com.ex.sn.sn.DTO.Auth.OtpGetReqDto;
import com.ex.sn.sn.DTO.Auth.OtpGetResDto;
import com.ex.sn.sn.DTO.Auth.OtpReqDto;
import com.ex.sn.sn.DTO.Auth.OtpResDto;
import com.ex.sn.sn.DTO.Auth.PasswordResetReqDto;
import com.ex.sn.sn.DTO.Auth.PasswordResetRequestReqDto;
import com.ex.sn.sn.DTO.Auth.PasswordResetRequestResDto;
import com.ex.sn.sn.DTO.Auth.RegistUserRepDto;
import com.ex.sn.sn.DTO.User.UserSignupResDto;
import com.ex.sn.sn.Response.BaseResponse;
import com.ex.sn.sn.Service.AuthService;
import com.ex.sn.sn.Utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService service;
    @Operation(summary = "API sign-up")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "register succesfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RegistUserRepDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid account", content = @Content) })

    @PostMapping("/signup")
    public BaseResponse<UserSignupResDto> register(@RequestBody @Valid RegistUserRepDto userInfo) {
        UserSignupResDto resDto = service.insertUser(userInfo);
        return BaseResponse.<UserSignupResDto>builder().result(resDto).message(CommonConstants.REGISTER_SUCCESS)
                .build();
    }
    	@PostMapping("/signin")
	@Operation(summary = "API sign-in", description = "return otp")
	@ApiResponse(responseCode = "200", description = "signin succesfully")
	@ApiResponse(responseCode = "401", description = "signin unsuccesfully")
	public BaseResponse<OtpResDto> generateOtp(@RequestBody @Valid OtpReqDto reqDto) {
		OtpResDto restDto = service.generateOTP(reqDto);
		return BaseResponse.<OtpResDto>builder().result(restDto).message(CommonConstants.GENERATE_OTP_SUCCESS).build();
	}
    
    @PostMapping("/verify-otp")
	@Operation(summary = "API verify otp", description = "return token if otp is right")
	@ApiResponse(responseCode = "200", description = "OTP is valid")
	@ApiResponse(responseCode = "401", description = "OTP invalid")
	public BaseResponse<OtpGetResDto> authenticate(@RequestBody @Valid OtpGetReqDto reqDto) {
		OtpGetResDto restDto = service.validateOTP(reqDto);
		return BaseResponse.<OtpGetResDto>builder().result(restDto).message(CommonConstants.SIGN_UP_SUCCESS).build();
	}

    @PostMapping("/password-reset-request")
	@Operation(summary = "API request reset password", description = "return token to reset password")
	@ApiResponse(responseCode = "200", description = "requesst reset password successfully")
	@ApiResponse(responseCode = "401", description = "request reset password have error")
	public BaseResponse<PasswordResetRequestResDto> resetPassword(
			@RequestBody @Valid PasswordResetRequestReqDto reqDto) {
		PasswordResetRequestResDto restDto = service.requestPasswordReset(reqDto);
		return BaseResponse.<PasswordResetRequestResDto>builder().result(restDto)
				.message(CommonConstants.REQUEST_RESET_PASSWORD_SUCCESS).build();
	}
    
    @PutMapping("/password-reset")
	@Operation(summary = "API reset password", description = "reset new password")
	@ApiResponse(responseCode = "200", description = "requesst password successfully")
	@ApiResponse(responseCode = "401", description = "reset password have error")
	public BaseResponse<?> resetNewPassword(@RequestBody @Valid PasswordResetReqDto reqDto) {
		service.resetNewPassword(reqDto);
		return BaseResponse.builder().message(CommonConstants.RESET_PASSWORD_SUCCESS).build();
	}

}

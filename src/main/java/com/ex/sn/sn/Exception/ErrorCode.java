package com.ex.sn.sn.Exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(101, "Uncategorized error", HttpStatus.UNAUTHORIZED),
    ERROR_IMPORT_DATA_EXCEL(101,"fail to import data to Excel file",HttpStatus.BAD_REQUEST),
    USER_EXISTED(102, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(102, "User not existed", HttpStatus.NOT_FOUND),
    POST_NOTEXISTED(102, "post not existed", HttpStatus.NOT_FOUND),
    POST_NOT_RIGHT(102, "you can not fix or delete other's post", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_RIGHT(102, "you can not fix or delete other's comment", HttpStatus.BAD_REQUEST),
    COMMENT_NOTEXISTED(102, "comment not existed", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(103, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(103, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    OTP_INVALID(103, "otp or userName is invalid", HttpStatus.UNAUTHORIZED),
    EXTENSION_INVALID(103, "file only be JPG or PNG", HttpStatus.BAD_REQUEST),
    POST_UPLOAD_WRONG(104, "post must have at least content or file", HttpStatus.BAD_REQUEST),
    ADD_FRIEND_YOUSELF(104, "You can not add friend youself", HttpStatus.BAD_REQUEST),
    ADD_REQUEST_TO_FRIEND(104, "can not add friend with your friend", HttpStatus.BAD_REQUEST),
    REMOVE_REQUEST_TO_FRIEND(104, "can not remove request when have been friend", HttpStatus.BAD_REQUEST),
    ADD_REQUEST_AGAIN_TO_FRIEND(104, "you have send request to your friend", HttpStatus.BAD_REQUEST),
    NOT_EXIST_REQUEST(104, "no request existed", HttpStatus.BAD_REQUEST),
    NOT_EXIST_FRIEND_REQUEST(104, "you have no request friend", HttpStatus.BAD_REQUEST),
    ACCEPT_YOUR_REQUEST(104, "you can not accept your request", HttpStatus.BAD_REQUEST),
    NOT_FRIEND(104, "not friend to remove", HttpStatus.BAD_REQUEST),
    REMOVE_FRIEND_YOURSELF(104, "can not remove friend yourself", HttpStatus.BAD_REQUEST),
    DELETE_IMAGE_POST_WRONG(104, "delete image not in post", HttpStatus.BAD_REQUEST),
    DELETE_IMAGE_COMMENT_WRONG(104, "delete image of comment is wrong", HttpStatus.BAD_REQUEST),
    REMOVE_FRIENDREQUEST_YOURSELF(104, "can not remove friend request yourself", HttpStatus.BAD_REQUEST),
    COMMENT_UPLOAD_WRONG(104, "comment must have at least content or file", HttpStatus.BAD_REQUEST),
    FILE_SIZE(105, "your file over 5mb", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(106, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(107, "You do not have permission", HttpStatus.FORBIDDEN),
    SIGNIN_ERROR(108, "User not existed", HttpStatus.UNAUTHORIZED),
    TOKEN_RESET_PSW_WRONG(108, "have error reset password", HttpStatus.UNAUTHORIZED),

    FILE_UPLOAD(112, "upload image error", HttpStatus.BAD_REQUEST),


    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}

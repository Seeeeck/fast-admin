package pers.syq.fastadmin.backstage.common.exception;

public enum ErrorCode {
    VALID_EXCEPTION(400,"Bad request"),
    UNAUTHORIZED(401,"Unauthorized"),
    FORBIDDEN(403,"Forbidden"),
    NOT_FOUND(404,"Not found"),
    INTERNAL_ERROR(500,"Internal error"),
    VERIFY_JWT_FAILED(1001, "Token verification failed"),
    TOKEN_EXPIRED(1002,"Token expired"),
    CAPTCHA_VALID_EXCEPTION(1101,"Error captcha"),
    USERNAME_EXISTS_EXCEPTION(1201,"This username already exists"),
    ILLEGAL_PASSWORD_EXCEPTION(1202,"Illegal password"),
    BAD_CREDENTIALS_PASSWORD(1203,"The username or password is not correct."),
    BAD_CREDENTIALS_BANED(403,"User is forbidden to login");

    private Integer code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
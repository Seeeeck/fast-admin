package pers.syq.fastadmin.backstage.common.exception;

public enum ErrorCode {
    VALID_EXCEPTION(400,"Bad request"),
    UNAUTHORIZED(401,"Unauthorized"),
    FORBIDDEN(403,"Forbidden"),
    NOT_FOUND(404,"Not found"),
    INTERNAL_ERROR(500,"Internal error"),
    VERIFY_JWT_FAILED(1001,"token验证失败"),
    TOKEN_EXPIRED(1002,"Token expired"),
    CAPTCHA_VALID_EXCEPTION(1101,"Error captcha");

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
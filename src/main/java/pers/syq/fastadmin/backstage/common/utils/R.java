package pers.syq.fastadmin.backstage.common.utils;

import lombok.Getter;
import lombok.Setter;
import pers.syq.fastadmin.backstage.common.exception.ErrorCode;


@Getter
@Setter
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    private R(){}

    public static R<?> ok(){
        R<?> r = new R<>();
        r.setCode(0);
        r.setMsg("success");
        return r;
    }

    public static <T> R<T> ok(T data){
        R<T> r = new R<>();
        r.setData(data);
        r.setCode(0);
        r.setMsg("success");
        return r;
    }

    public static R<?> error(){
        R<?> r = new R<>();
        r.setCode(500);
        r.setMsg("unknown error");
        return r;
    }

    public static <T> R<T> error(T data){
        R<T> r = new R<>();
        r.setData(data);
        r.setCode(500);
        r.setMsg("unknown error");
        return r;
    }

    public R<?> errorCode(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        return this;
    }
    public R<?> code(int code){
        this.code = code;
        return this;
    }

    public R<?> msg(String msg){
        this.msg = msg;
        return this;
    }


}

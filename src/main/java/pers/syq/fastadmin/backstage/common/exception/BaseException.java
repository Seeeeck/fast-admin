package pers.syq.fastadmin.backstage.common.exception;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final transient HashMap<String, Object> data = new HashMap<>();

    public BaseException(ErrorCode errorCode){
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, Map<String, Object> data) {
        this(errorCode);
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    public BaseException(ErrorCode errorCode, Map<String, Object> data, Throwable cause) {
        super(errorCode.getMsg(), cause);
        this.errorCode = errorCode;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }


}

package pers.syq.fastadmin.backstage.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pers.syq.fastadmin.backstage.common.utils.R;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({BindException.class})
    public R<?> handleBindExceptionException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String,Object> map = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return R.error(map).errorCode(ErrorCode.VALID_EXCEPTION);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public R<?> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        HashMap<String,Object> map = new HashMap<>();
        for (ConstraintViolation<?> violation : constraintViolations) {
            String[] strings = violation.getPropertyPath().toString().split("\\.");
            map.put(strings[1],violation.getMessage());
        }
        return R.error(map).errorCode(ErrorCode.VALID_EXCEPTION);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public R<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return R.error().errorCode(ErrorCode.VALID_EXCEPTION).msg(ex.getMessage());
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public R<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(ex.getName(),"valid exception");
        return  R.error(map).errorCode(ErrorCode.VALID_EXCEPTION);
    }

    @ExceptionHandler({BaseException.class})
    public R<?> handleBaseException(BaseException ex){
        return R.error(ex.getData()).errorCode(ex.getErrorCode());
    }
    @ExceptionHandler({BadCredentialsException.class})
    public R<?> handleBadCredentialsException(BadCredentialsException ex){
        return R.error().msg(ex.getMessage()).code(401);
    }

    @ExceptionHandler({AccessDeniedException.class,AuthenticationException.class})
    public void handleSecurityException(Exception ex) throws Exception {
        throw ex;
    }


    @ExceptionHandler({Exception.class})
    public R<?> handleException(Exception ex) {
        ex.printStackTrace();
        HashMap<String, Object> map = new HashMap<>();
        map.put("cause",ex.getCause());
        return R.error(map).code(500).msg(ex.getMessage());
    }

}

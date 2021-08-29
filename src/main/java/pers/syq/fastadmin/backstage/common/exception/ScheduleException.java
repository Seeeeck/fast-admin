package pers.syq.fastadmin.backstage.common.exception;


public class ScheduleException extends RuntimeException{
    public ScheduleException(Throwable cause, String msg) {
        super(msg,cause);
    }
}

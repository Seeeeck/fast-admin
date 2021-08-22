package pers.syq.fastadmin.backstage.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.github.houbb.sensitive.core.api.SensitiveUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.syq.fastadmin.backstage.annotation.sensitive.SensitiveClass;
import pers.syq.fastadmin.backstage.annotation.SysLog;
import pers.syq.fastadmin.backstage.common.utils.HttpContextUtils;
import pers.syq.fastadmin.backstage.common.utils.IPUtils;
import pers.syq.fastadmin.backstage.common.utils.SecurityUtils;
import pers.syq.fastadmin.backstage.entity.SysLogEntity;
import pers.syq.fastadmin.backstage.service.SysLogService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(pers.syq.fastadmin.backstage.annotation.SysLog)")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        saveSysLog(point,time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint point, long time) {
        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setTime(time);
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog != null){
            //注解描述
            sysLogEntity.setOperation(sysLog.value());
        }
        String methodName = method.getName();
        String className = point.getTarget().getClass().getName();
        sysLogEntity.setMethod(className + "." + methodName + "()");
        Object[] args = point.getArgs();
        Object[] objects = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i].getClass().getAnnotation(SensitiveClass.class) != null){
                objects[i] = SensitiveUtil.desCopy(args[i]);
            }else {
                objects[i] = args[i];
            }
        }
        try {
            String params = JSONUtil.toJsonStr(objects);
            sysLogEntity.setParams(params);
        }catch (Exception e){
            log.error(DateUtil.now() + ":" + this.getClass().getName()+"."+ Thread.currentThread().getStackTrace()[1].getMethodName()+" -> " + e);
        }
        HttpServletRequest servletRequest = HttpContextUtils.getHttpServletRequest();
        sysLogEntity.setIp(IPUtils.getIpAddr(servletRequest));
        SecurityUtils.getUserName().ifPresent(sysLogEntity::setUsername);
        sysLogService.save(sysLogEntity);
    }

}

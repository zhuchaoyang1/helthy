package com.zcy.cn.annotation.aop;

import com.zcy.cn.bean.Logs;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.LogService;
import com.zcy.cn.vo.AnnotationUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class TokenModelAspect {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.zcy.cn.annotation.TokenModel)")
        private void pointcut() {
    }

    /**
     * 使用环绕增强
     * 只对前置执行做业务调整
     * <p>
     * TODO 环绕异常因为已经对Controller执行进行try catch因此 错误信息不会被ControllerAdvice捕获，因此进行Log记录 邮件记录等操作
     *
     * @param joinPoint
     */
    @Around("pointcut()")
    public Object advice(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        AnnotationUser annotationUser = new AnnotationUser();

        Arrays.asList(args).forEach(var -> {
            if (var instanceof HttpServletRequest) {
                Object objOpenId = ((HttpServletRequest) var).getAttribute("openId");
                annotationUser.setOpenId(objOpenId == null ? null : objOpenId.toString());
                Object objUId = ((HttpServletRequest) var).getAttribute("uId");
                annotationUser.setUId(objUId == null ? null : Long.valueOf(objUId.toString()));
            }
        });

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof AnnotationUser) {
                args[i] = annotationUser;
                break;
            }
        }

        try {
            return joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            // Controller异常会走该方法
            log.error("\n方法名：{}, \n异常记录：{}", joinPoint.getSignature().getName(), throwable.getMessage());
            Logs logs = Logs.builder().hasSendEmail(false).leavel("ERROR").lId(null)
                    .platforms("WEB").logInfo("方法名：" + joinPoint.getSignature().getName() + "异常信息：" + throwable.getMessage()).build();
            logService.save(logs);
        }

        return ResultHttp.builder().code(0).result("网路错误，请稍等重试").build();
    }
}

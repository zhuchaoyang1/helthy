package com.zcy.cn.annotation.aop;

import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.vo.AnnotationUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class TokenModelAspect {

    @Pointcut("@annotation(com.zcy.cn.annotation.TokenModel)")
    private void pointcut() {
    }

    /**
     * 使用环绕增强
     * 只对前置执行做业务调整
     *
     * @param joinPoint
     */
    @Around("pointcut()")
    public Object advice(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        AnnotationUser annotationUser = new AnnotationUser();

        Arrays.asList(args).forEach(var -> {
            if (var instanceof HttpServletRequest) {
                annotationUser.setOpenId(((HttpServletRequest) var).getAttribute("openId").toString());
                Object objUId = ((HttpServletRequest) var).getAttribute("userId");
                annotationUser.setUId(objUId == null ? null : Integer.parseInt(objUId.toString()));
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
        }

        return ResultHttp.builder().code(0).result("数据库中已存在记录").build();
    }
}

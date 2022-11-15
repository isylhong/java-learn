package com.autumn.learn.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import java.util.Arrays;

/**
 * 定义切面
 *
 * @author yl
 * @since 2022-11-14 22:58
 */
public class MyAspect {
    /**
     * 前置通知：目标方法调用前执行
     */
    public void beforeAdvice(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        System.out.println("----- 前置通知 -----, signature: " + signature.toString() + ", args: " + Arrays.asList(args));
    }

    /**
     * 后置通知：目标方法执行完后被调用
     */
    public void afterAdvice() {
        System.out.println("----- 后置通知 -----");
    }

    /**
     * 环绕通知：可决定目标方法是否执行，什么时候执行，执行时是否需要替换参数，执行完毕是否需要替换返回值
     *
     * @param joinPoint 第一个参数的类型必须为 org.aspectj.lang.ProceedingJoinPoint
     */
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        try {
            System.out.println("----- 环绕前置通知 -----");
            Object proceed = joinPoint.proceed(args); // 执行目标方法
            System.out.println("----- 环绕后置通知 -----");
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * 后置返回通知：
     *
     * @param joinPoint 如果第一个参数类型为JoinPoint，则第二个参数代表返回值；否则，默认第一个参数为注解属性returning的值
     * @param result    只有目标方法返回值类型与通知方法相应参数类型相同时，才执行后置返回通知；否则不执行
     */
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        System.out.println("----- 后置返回通知，返回值：" + result);
    }

    /**
     * 后置异常通知
     *
     * @param joinPoint
     * @param e         只有目标方法抛出的异常类型与通知方法相应参数类型相同时，才执行后置异常通知；否则不执行
     */
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception e) {
        System.out.println("----- 后置异常通知 -----, " + e.toString());
    }
}

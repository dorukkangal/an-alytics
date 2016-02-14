package com.dorukkangal.analytics.aspect;

import com.dorukkangal.analytics.AnalyticsManager;
import com.dorukkangal.analytics.annotation.TrackScreen;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Doruk Kangal
 * @since 1.0.0
 */
@Aspect
public final class TrackScreenAspect {

    public static TrackScreenAspect aspectOf() {
        return new TrackScreenAspect();
    }

    private TrackScreenAspect() {
    }

    @Pointcut("execution(@com.dorukkangal.analytics.annotation.TrackScreen * *(..))")
    public void method() {
    }

    @Pointcut("execution(@com.dorukkangal.analytics.annotation.TrackScreen *.new(..))")
    public void constructor() {
    }

    @Around("method() || constructor()")
    public Object sendTrack(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object object = joinPoint.proceed();

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final TrackScreen trackScreen = methodSignature.getMethod().getAnnotation(TrackScreen.class);
        AnalyticsManager.getInstance().trackScreen(
                trackScreen.name()
        );
        return object;
    }
}

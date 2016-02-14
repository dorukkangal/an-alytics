package com.dorukkangal.analytics.aspect;

import com.dorukkangal.analytics.AnalyticsManager;
import com.dorukkangal.analytics.annotation.TrackEvent;

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
public final class TrackEventAspect {

    public static TrackEventAspect aspectOf() {
        return new TrackEventAspect();
    }

    private TrackEventAspect() {
    }

    @Pointcut("execution(@com.dorukkangal.analytics.annotation.TrackEvent * *(..))")
    public void method() {
    }

    @Pointcut("execution(@com.dorukkangal.analytics.annotation.TrackEvent *.new(..))")
    public void constructor() {
    }

    @Around("method() || constructor()")
    public Object sendTrack(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object object = joinPoint.proceed();

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final TrackEvent trackEvent = methodSignature.getMethod().getAnnotation(TrackEvent.class);
        AnalyticsManager.getInstance().trackEvent(
                trackEvent.category(),
                trackEvent.action(),
                trackEvent.label(),
                trackEvent.value()
        );
        return object;
    }
}

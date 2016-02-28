package com.dorukkangal.analytics.aspect;

import com.dorukkangal.analytics.AnalyticsManager;

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

    private static final String APPLICATION_POINTCUT_METHOD = "execution(@com.dorukkangal.analytics.annotation.TrackEvent * *(..))";
    private static final String APPLICATION_POINTCUT_CONSTRUCTOR = "execution(@com.dorukkangal.analytics.annotation.TrackEvent *.new(..))";
    private static final String LIBRARY_POINTCUT_METHOD = "execution(@com.dorukkangal.analytics.annotation.library.TrackEvent * *(..))";
    private static final String LIBRARY_POINTCUT_CONSTRUCTOR = "execution(@com.dorukkangal.analytics.annotation.library.TrackEvent *.new(..))";

    public static TrackEventAspect aspectOf() {
        return new TrackEventAspect();
    }

    private TrackEventAspect() {
    }

    @Pointcut(APPLICATION_POINTCUT_METHOD)
    public void applicationMethod() {
    }

    @Pointcut(APPLICATION_POINTCUT_CONSTRUCTOR)
    public void applicationConstructor() {
    }

    @Around("applicationMethod() || applicationConstructor()")
    public Object sendApplicationTrack(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object object = joinPoint.proceed();

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final com.dorukkangal.analytics.annotation.TrackEvent trackEvent
                = methodSignature.getMethod().getAnnotation(com.dorukkangal.analytics.annotation.TrackEvent.class);

        AnalyticsManager.getInstance().trackEvent(
                trackEvent.category(),
                trackEvent.action(),
                trackEvent.label(),
                trackEvent.value()
        );

        return object;
    }

    @Pointcut(LIBRARY_POINTCUT_METHOD)
    public void libraryMethod() {
    }

    @Pointcut(LIBRARY_POINTCUT_CONSTRUCTOR)
    public void libraryConstructor() {
    }

    @Around("libraryMethod() || libraryConstructor()")
    public Object sendLibraryTrack(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object object = joinPoint.proceed();

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final com.dorukkangal.analytics.annotation.library.TrackEvent trackEvent
                = methodSignature.getMethod().getAnnotation(com.dorukkangal.analytics.annotation.library.TrackEvent.class);

        AnalyticsManager.getInstance().trackEvent(
                trackEvent.category(),
                trackEvent.action(),
                trackEvent.label(),
                trackEvent.value()
        );

        return object;
    }
}

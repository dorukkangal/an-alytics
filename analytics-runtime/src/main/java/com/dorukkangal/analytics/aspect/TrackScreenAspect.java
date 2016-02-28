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
public final class TrackScreenAspect {

    private static final String APPLICATION_POINTCUT_METHOD = "execution(@com.dorukkangal.analytics.annotation.TrackScreen * *(..))";
    private static final String APPLICATION_POINTCUT_CONSTRUCTOR = "execution(@com.dorukkangal.analytics.annotation.TrackScreen *.new(..))";
    private static final String LIBRARY_POINTCUT_METHOD = "execution(@com.dorukkangal.analytics.annotation.library.TrackScreen * *(..))";
    private static final String LIBRARY_POINTCUT_CONSTRUCTOR = "execution(@com.dorukkangal.analytics.annotation.library.TrackScreen *.new(..))";

    public static TrackScreenAspect aspectOf() {
        return new TrackScreenAspect();
    }

    private TrackScreenAspect() {
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
        final com.dorukkangal.analytics.annotation.TrackScreen trackScreen
                = methodSignature.getMethod().getAnnotation(com.dorukkangal.analytics.annotation.TrackScreen.class);

        AnalyticsManager.getInstance().trackScreen(
                trackScreen.name()
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
        final com.dorukkangal.analytics.annotation.library.TrackScreen trackScreen
                = methodSignature.getMethod().getAnnotation(com.dorukkangal.analytics.annotation.library.TrackScreen.class);

        AnalyticsManager.getInstance().trackScreen(
                trackScreen.name()
        );

        return object;
    }
}

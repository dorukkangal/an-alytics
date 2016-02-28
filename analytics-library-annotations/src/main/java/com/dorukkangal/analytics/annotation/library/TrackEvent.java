package com.dorukkangal.analytics.annotation.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Doruk Kangal
 * @since 2.0.0
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackEvent {

    String category() default "";

    String action() default "";

    String label() default "";

    long value() default 0;
}

package com.dorukkangal.analytics.annotation;

import android.support.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Doruk Kangal
 * @since 1.0.0
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackEvent {

    @StringRes int category() default 0;

    @StringRes int action() default 0;

    @StringRes int label() default 0;

    long value() default 0;
}

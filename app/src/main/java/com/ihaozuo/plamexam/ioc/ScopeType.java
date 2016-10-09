package com.ihaozuo.plamexam.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by xiongwei1 on 2016/8/5.
 */
public interface ScopeType {
    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ActivityScope {
    }

    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FragmentScope {
    }

    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CustomScope {
    }
}

;
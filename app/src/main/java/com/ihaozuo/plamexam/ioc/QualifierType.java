package com.ihaozuo.plamexam.ioc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

public interface QualifierType {
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface testQualifier{}

}
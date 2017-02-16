package com.github.kburger.fairdatapoint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Main entry point to enable the FAIR Datapoint functionality in a Spring application.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FairDatapointConfig.class)
public @interface EnableFairDatapoint {
}

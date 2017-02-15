package com.github.kburger.fairdatapoint.io.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the {@code rdf:type} for the class.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Type {
    /**
     * Type definition URI for the class. The format should be an absolute URI.
     * @return type definition URI for the class.
     */
    String value();
}

package com.github.kburger.fairdatapoint.io.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;

/**
 * Indicates this field is to be serialized with the specified predicate URI and, in case of a
 * literal, the specified datatype.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Predicate {
    /**
     * Predicate URI for the field value. The format should be an absolute URI.
     * @return predicate URI for the field value.
     */
    String value();
    
    /**
     * Flag to determine whether the field value is a {@link Literal} or a {@link IRI URI}. Defaults
     * to {@code false}.
     * @return flag that indicates whether the field value is a Literal or a URI.
     */
    boolean isLiteral() default false;
    
    /**
     * In case of a {@link #isLiteral() literal}, defines the datatype for the {@link Literal}.
     * Defaults to xsd:string.
     * @return the datatype for the field value.
     */
    String datatype() default "xsd:string";
}

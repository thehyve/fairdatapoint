package com.github.kburger.fairdatapoint.model;

import com.github.kburger.fairdatapoint.io.annotation.Predicate;

/**
 * Immutable base class for all metadata objects.
 */
public abstract class Metadata {
    /** dcterms:title */
    @Predicate(value = "http://purl.org/dc/terms/title", isLiteral = true)
    protected String title;
    
    public String getTitle() {
        return title;
    }
    
    /**
     * Builder pattern base class for {@link Metadata} classes.
     * @param <T> {@code Metadata} type the {@code Builder} will produce.
     * @param <B> {@code Builder} implementation for {@code T}.
     */
    @SuppressWarnings("unchecked")
    public abstract static class Builder<T extends Metadata, B extends Builder<T, B>> {
        /** Metadata object under construction. */
        protected T metadata;
        
        public B title(String title) {
            metadata.title = title;
            return (B)this;
        }
        
        /**
         * Returns the metadata object of type {@code <T>}, populated through the builder methods.
         * @return a non-null metadata objects.
         */
        public T build() {
            return metadata;
        }
    }
}

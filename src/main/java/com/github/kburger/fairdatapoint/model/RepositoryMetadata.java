package com.github.kburger.fairdatapoint.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.github.kburger.fairdatapoint.io.annotation.Predicate;
import com.github.kburger.fairdatapoint.io.annotation.Type;

/**
 * Immutable class that represents FAIR datapoint repository metadata.
 */
@Type("http://www.re3data.org/schema/3-0#Repository")
public class RepositoryMetadata extends Metadata {
    /** r3d:dataCatalog */
    @Predicate("http://www.re3data.org/schema/3-0#dataCatalog")
    private List<URI> catalogs;
    
    /**
     * Constructor for the RepositoryMetadata. Marked private to ensure instances are created
     * through the {@link Builder}.
     */
    private RepositoryMetadata() {
        catalogs = new ArrayList<>();
    }
    
    public List<URI> getCatalogs() {
        return catalogs;
    }
    
    /**
     * Creates a new {@link Builder} instance to construct immutable {@code RepositoryMetadata}
     * instances.
     * @return an empty {@code Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * {@link Metadata.Builder} implementation that produces {@link RepositoryMetadata} objects.
     */
    public static class Builder extends Metadata.Builder<RepositoryMetadata, RepositoryMetadata.Builder> {
        private Builder() {
            metadata = new RepositoryMetadata();
        }
        
        public Builder catalog(URI catalog) {
            metadata.catalogs.add(catalog);
            return this;
        }
    }
}

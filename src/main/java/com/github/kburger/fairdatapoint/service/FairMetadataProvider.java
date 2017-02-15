package com.github.kburger.fairdatapoint.service;

import com.github.kburger.fairdatapoint.model.RepositoryMetadata;

/**
 * The metadata provider gives access to the different levels of metadata. Primary interaction for
 * implementing applications.
 */
public interface FairMetadataProvider {
    /**
     * Provides the metadata information for the first layer of the FAIR datapoint.
     * @return repository metadata.
     */
    RepositoryMetadata getRepositoryMetadata();
}

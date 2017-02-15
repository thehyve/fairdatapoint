package com.github.kburger.fairdatapoint.io;

import com.github.kburger.fairdatapoint.model.Metadata;

/**
 * Writer for {@link Metadata} classes.
 */
public interface MetadataWriter {
    /**
     * Serializes a {@link Metadata} object to {@code string}.
     * @param metadata {@code Metadata} instance.
     * @return string serialization of the metadata.
     */
    String write(Metadata metadata);
}

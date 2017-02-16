package com.github.kburger.fairdatapoint.web.converter;

import org.eclipse.rdf4j.rio.RDFFormat;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class TurtleMetadataHttpMessageConverter extends MetadataHttpMessageConverter {
    public TurtleMetadataHttpMessageConverter() {
        super(RDFFormat.TURTLE);
    }
}

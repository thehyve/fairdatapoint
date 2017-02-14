package com.github.kburger.fairdatapoint.model

import static com.github.kburger.fairdatapoint.model.MetadataSpec.*

import spock.lang.Specification

class RepositoryMetadataSpec extends Specification {
    def "builder instance remains equal"() {
        when:
        def builder = RepositoryMetadata.builder()
        
        then:
        builder.catalog(null) == builder
    }
    
    def "builder methods produces equivalent values in the build object"() {
        when:
        def metadata = RepositoryMetadata.builder()
                .title(EXAMPLE_STRING)
                .catalog(EXAMPLE_URI)
                .build()
        then:
        with (metadata) {
            title == EXAMPLE_STRING
            catalogs == [EXAMPLE_URI]
        }
    }
}

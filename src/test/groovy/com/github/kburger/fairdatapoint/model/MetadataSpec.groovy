package com.github.kburger.fairdatapoint.model

import spock.lang.Shared
import spock.lang.Specification

class MetadataSpec extends Specification {
    static URI EXAMPLE_URI = URI.create("http://example.org")
    static String EXAMPLE_STRING = "example"
    
    def "builder instance remains equal"() {
        when:
        def builder = TestMetadata.builder()
        
        then:
        builder.title(EXAMPLE_STRING) == builder
    }
    
    def "builder methods produces equivalent values in the build object"() {
        when:
        def metadata = TestMetadata.builder()
                .title(EXAMPLE_STRING)
                .build()
        
        then:
        with (metadata) {
            title == EXAMPLE_STRING
        }
    }
}

class TestMetadata extends Metadata {
    static Builder builder() {
        return new Builder()
    }
    static class Builder extends Metadata.Builder<TestMetadata, TestMetadata.Builder> {
        Builder() {
            metadata = new TestMetadata()
        }
    }
}
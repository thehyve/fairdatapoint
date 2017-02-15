package com.github.kburger.fairdatapoint.io.annotation

import com.github.kburger.fairdatapoint.model.Metadata
import com.github.kburger.fairdatapoint.model.RepositoryMetadata

import spock.lang.Shared
import spock.lang.Specification

class AnnotationMetadataWriterImplSpec extends Specification {
    @Shared
    AnnotationMetadataWriterImpl writer
    
    def setup() {
        writer = new AnnotationMetadataWriterImpl()
    }
    
    def "rdf output matches the metadata object"() {
        when:
        def output = writer.write(RepositoryMetadata.builder().title("example.com").build())
        
        then:
        output == """\
                @prefix dcterms: <http://purl.org/dc/terms/> .
                
                <http://example.com/fdp> dcterms:title "example.com"^^<xsd:string> .
                """.stripIndent()
    }
    
    def "repeated predicates are written to output"() {
        when:
        def output = writer.write(RepositoryMetadata.builder()
                .catalog(URI.create("http://example.com/1"))
                .catalog(URI.create("http://example.com/2"))
                .build())
        
        then:
        output.contains("<http://example.com/1>")
        output.contains("<http://example.com/2>")
    }
    
    def "cache should prevent reanalysis of classes"() {
        setup:
        def first = RepositoryMetadata.builder().build()
        def second = Mock(Metadata)
        
        when:
        writer.write(first)
        then:
        // cached analysis of Metadata and RepositoryMetadata class
        writer.cache.size() == 2
        
        when:
        writer.write(second)
        then:
        // cached analysis of the Mock class added
        writer.cache.size() == 3
    }
    
    def "non-annotated fields are ignored"() {
        setup:
        def metadata = new NoAnnotationMetadata()
        metadata.value = "test"
        
        when:
        def output = writer.write(metadata)
        
        then:
        output.contains("test") == false
    }
}

class NoAnnotationMetadata extends Metadata {
    private String value
    
    public String getValue() {
        value
    }
}

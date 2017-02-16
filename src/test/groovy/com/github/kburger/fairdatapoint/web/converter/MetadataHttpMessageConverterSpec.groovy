package com.github.kburger.fairdatapoint.web.converter

import org.eclipse.rdf4j.rio.RDFFormat
import org.springframework.mock.http.MockHttpInputMessage
import org.springframework.mock.http.MockHttpOutputMessage

import com.github.kburger.fairdatapoint.io.annotation.AnnotationMetadataWriterImpl
import com.github.kburger.fairdatapoint.model.Metadata
import com.github.kburger.fairdatapoint.model.RepositoryMetadata

import spock.lang.Shared
import spock.lang.Specification

class MetadataHttpMessageConverterSpec extends Specification {
    @Shared MetadataHttpMessageConverter converter = new MetadataHttpMessageConverter(RDFFormat.TURTLE) {}
    
    def "converter supports classes of type Metadata"() {
        expect:
        converter.supports(Metadata) == true
        converter.supports(RepositoryMetadata) == true
        converter.supports(String) == false
        converter.supports(Map) == false
    }
    
    def "converter writes metadata to string"() {
        setup:
        def metadata = RepositoryMetadata.builder()
                .title("test")
                .build()
        def outputStream = new MockHttpOutputMessage();
        def writer = Spy(AnnotationMetadataWriterImpl)
        converter.metadataWriter = writer
                
        when:
        converter.writeInternal(metadata, outputStream)
        
        then:
        1 * writer.write(metadata)
        outputStream.getBodyAsString() == """\
            @prefix dcterms: <http://purl.org/dc/terms/> .
            
            <http://example.com/fdp> dcterms:title "test"^^<xsd:string> .
            """.stripIndent()
    }
    
    def "converter doesn't support read operation"() {
        when:
        converter.readInternal(Metadata, new MockHttpInputMessage())
        
        then:
        thrown(UnsupportedOperationException)
    }
}

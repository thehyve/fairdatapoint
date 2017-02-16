package com.github.kburger.fairdatapoint.web.converter

import org.springframework.http.MediaType

import spock.lang.Shared
import spock.lang.Specification

class TurtleMetadataHttpMessageConverterSpec extends Specification {
    @Shared def converter = new TurtleMetadataHttpMessageConverter()
    
    def "converter produces the right mediatype"() {
        expect:
        converter.supportedMediaTypes.contains(MediaType.parseMediaType('text/turtle'))
    }
}

package com.github.kburger.fairdatapoint.web.controller

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import com.github.kburger.fairdatapoint.service.FairMetadataProvider

import spock.lang.Shared
import spock.lang.Specification

class RepositoryControllerSpec extends Specification {
    FairMetadataProvider provider = Mock()
    MockMvc mockMvc
    
    def setup() {
        def controller = new RepositoryController()
        controller.provider = provider
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }
    
    def "provider is invoked once"() {
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/fdp"))
        
        then:
        1 * provider.getRepositoryMetadata()
        response.andExpect(MockMvcResultMatchers.status().isOk())
    }
}

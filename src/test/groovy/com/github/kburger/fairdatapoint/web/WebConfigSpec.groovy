package com.github.kburger.fairdatapoint.web

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc

import com.github.kburger.fairdatapoint.io.MetadataWriter
import com.github.kburger.fairdatapoint.io.annotation.AnnotationMetadataWriterImpl
import com.github.kburger.fairdatapoint.model.RepositoryMetadata
import com.github.kburger.fairdatapoint.service.FairMetadataProvider

import spock.lang.Specification

@WebAppConfiguration
@ContextConfiguration(classes = TestContext)
class WebConfigSpec extends Specification {
    @Autowired
    WebApplicationContext wac
    
    MockMvc mockMvc
    
    def setup() {
        mockMvc = webAppContextSetup(wac).build()
    }
    
    def "root controller is responding"() {
        when:
        def response = mockMvc.perform(get("/fdp"))
        
        then:
        response.andExpect(status().isOk())
                .andExpect(content().contentType("text/turtle"))
    }
}

@Configuration
@EnableWebMvc
@Import(WebConfig)
class TestContext {
    @Bean
    FairMetadataProvider provider() {
        new FairMetadataProvider() {
            RepositoryMetadata getRepositoryMetadata() {
                RepositoryMetadata.builder().title("test").build()
            }
        }
    }
    
    @Bean
    MetadataWriter writer() {
        new AnnotationMetadataWriterImpl()
    }
}
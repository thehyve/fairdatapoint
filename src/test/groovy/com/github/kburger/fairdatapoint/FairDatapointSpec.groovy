package com.github.kburger.fairdatapoint

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

import com.github.kburger.fairdatapoint.model.RepositoryMetadata
import com.github.kburger.fairdatapoint.service.FairMetadataProvider

import spock.lang.Specification

@WebAppConfiguration
@ContextConfiguration(classes = TestContext)
class FairDatapointSpec extends Specification {
    @Autowired
    ApplicationContext context
    
    def "controller beans are configured"() {
        expect:
        context.containsBean("repositoryController")
    }
}

@Configuration
@EnableWebMvc
@EnableFairDatapoint
class TestContext {
    @Bean
    FairMetadataProvider provider() {
        new FairMetadataProvider() {
            RepositoryMetadata getRepositoryMetadata() {
                RepositoryMetadata.builder().build()
            }
        }
    }
}
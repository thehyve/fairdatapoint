package com.github.kburger.fairdatapoint.web;

import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.github.kburger.fairdatapoint.web.converter.MetadataHttpMessageConverter;

@Configuration
@ComponentScan
public class WebConfig implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);
    
    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    @Autowired
    private ContentNegotiationManager contentNegotiationManager;
    @Autowired
    private List<MetadataHttpMessageConverter> metadataConverters;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("registering metadata messageconverters");
        requestMappingHandlerAdapter.getMessageConverters().addAll(metadataConverters);
        
        ContentNegotiationManagerFactoryBean factory = new ContentNegotiationManagerFactoryBean();
        factory.setServletContext(servletContext);
        factory.setFavorPathExtension(true);
        
        // register each message converter with the intermediate factory
        for (MetadataHttpMessageConverter converter : metadataConverters) {
            converter.configureContentNegotiation(factory);
        }
        
        factory.afterPropertiesSet();
        ContentNegotiationManager intermediate = factory.getObject();
        contentNegotiationManager.getStrategies().addAll(intermediate.getStrategies());
    }
}

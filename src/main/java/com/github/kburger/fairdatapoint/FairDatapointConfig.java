package com.github.kburger.fairdatapoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.kburger.fairdatapoint.io.MetadataWriter;
import com.github.kburger.fairdatapoint.io.annotation.AnnotationMetadataWriterImpl;
import com.github.kburger.fairdatapoint.web.WebConfig;

@Configuration
@Import({WebConfig.class})
public class FairDatapointConfig {
    @Bean
    public MetadataWriter writer() {
        return new AnnotationMetadataWriterImpl();
    }
}

package com.github.kburger.fairdatapoint.example.spring;

import java.net.URI;

import org.springframework.stereotype.Component;

import com.github.kburger.fairdatapoint.model.RepositoryMetadata;
import com.github.kburger.fairdatapoint.service.FairMetadataProvider;

@Component
public class ExampleFairMetadataProvider implements FairMetadataProvider {
    @Override
    public RepositoryMetadata getRepositoryMetadata() {
        return RepositoryMetadata.builder()
                .title("Example repository")
                .catalog(URI.create("http://example.com/fdp/my-catalog"))
                .build();
    }
}

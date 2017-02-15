package com.github.kburger.fairdatapoint.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kburger.fairdatapoint.model.RepositoryMetadata;
import com.github.kburger.fairdatapoint.service.FairMetadataProvider;

@RestController
@RequestMapping("${fdp.root:/fdp}")
public class RepositoryController {
    @Autowired
    private FairMetadataProvider provider;
    
    @RequestMapping()
    public RepositoryMetadata getRepositoryMetadata() {
        return provider.getRepositoryMetadata();
    }
}

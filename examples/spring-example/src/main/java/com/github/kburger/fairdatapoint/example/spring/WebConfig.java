package com.github.kburger.fairdatapoint.example.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.kburger.fairdatapoint.EnableFairDatapoint;
import com.github.kburger.fairdatapoint.html.EnableFairDatapointHtml;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
@EnableWebMvc
@EnableFairDatapoint
@EnableFairDatapointHtml
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*");
    }
}

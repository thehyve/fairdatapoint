package com.github.kburger.fairdatapoint.example.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return array(WebConfig.class);
    }

    @Override
    protected String[] getServletMappings() {
        return array("/");
    }
    
    @SafeVarargs
    private static <T> T[] array(T... array) {
        return array;
    }
}

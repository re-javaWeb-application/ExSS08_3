package com.re.bt3.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.io.File;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final String UPLOAD_LOCATION = "C:/RikkeiFood_Temp/";
    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;
    private static final long MAX_REQUEST_SIZE = 10L * 1024 * 1024;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        new File(UPLOAD_LOCATION).mkdirs();

        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                UPLOAD_LOCATION,
                MAX_FILE_SIZE,
                MAX_REQUEST_SIZE,
                0
        );
        registration.setMultipartConfig(multipartConfig);
    }
}

package com.gruppe6.econsult.Anfragenverwaltung.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static files from the "public/photo" directory
        registry.addResourceHandler("/public/photo/**")
                .addResourceLocations("file:public/photo/");
    }
}

package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL path /files/** to the physical directory <project-root>/files
        String dirUri = Paths.get(System.getProperty("user.dir"), "files").toUri().toString();
        registry.addResourceHandler("/files/**")
                .addResourceLocations(dirUri);

        // Map URL path /pages/** to classpath static pages directory
        registry.addResourceHandler("/pages/**")
                .addResourceLocations("classpath:/static/pages/");
    }
}

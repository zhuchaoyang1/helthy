package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImagesUploadConfig implements WebMvcConfigurer {
    @Value("${local.upload.img}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = System.getProperty("user.dir") + filePath + "/img/";
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + path);
    }
}

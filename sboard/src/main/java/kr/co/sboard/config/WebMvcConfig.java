package kr.co.sboard.config;

import kr.co.sboard.intercepter.AppInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.profile.path}")
    private String profilePath;

    @Autowired
    private AppInfo appInfo;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AppInfoInterceptor(appInfo));

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/sboard/profile/")
                .addResourceLocations("file:///C:/Users/java/Desktop/workspace/Spring/sboard/profile/");
    }
}

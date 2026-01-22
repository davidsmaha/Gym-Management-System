package gym.gym.configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:3000") // React app origin
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // Allowed methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Allow credentials like cookies
    }
}


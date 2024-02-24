package com.genesisairport.reservation.config;


import com.genesisairport.reservation.common.SessionInterceptor;
import com.genesisairport.reservation.common.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SessionUtil sessionUtil;

    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:3001",
                        "https://reservation.genesis-airport.com",
                        "https://admin.genesis-airport.com")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(86400);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor(sessionUtil))
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/v1/login", "/",
                        "/v2/admin/account/login", "/v2/admin/account/session-validation");
    }
}

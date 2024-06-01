package com.lari.topproductsservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RestTemplate restTemplateWithAuth(RestTemplateBuilder builder) {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add((request, body, execution) -> {
            request.getHeaders().set("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiZXhwIjoxNzE3MjIyOTc4LCJpYXQiOjE3MTcyMjI2NzgsImlzcyI6IkFmZm9yZG1lZCIsImp0aSI6IjgwMDhjYjUyLTQ0NjUtNDcyZC05NTk3LWQ5NDRjYTFmYjI2NSIsInN1YiI6Im1vaGFtbWFkLjIxMjVpdDEwMzlAa2lldC5lZHUifSwiY29tcGFueU5hbWUiOiJqYXZhTGFyaSIsImNsaWVudElEIjoiODAwOGNiNTItNDQ2NS00NzJkLTk1OTctZDk0NGNhMWZiMjY1IiwiY2xpZW50U2VjcmV0IjoiRFNoakVabXlybFpDdUJZdCIsIm93bmVyTmFtZSI6IkFtYWFuIiwib3duZXJFbWFpbCI6Im1vaGFtbWFkLjIxMjVpdDEwMzlAa2lldC5lZHUiLCJyb2xsTm8iOiIyMTAwMjkwMTMwMTAxIn0.BD5e6WeUF3XXGQgaU3dZ-eIRbT2qQpPHxOdltVNAirM");
            return execution.execute(request, body);
        });
        return builder.additionalInterceptors(interceptors).build();
    }
}


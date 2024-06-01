package com.lari.averagecalculator.service;

import com.lari.averagecalculator.constants.ApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class NumberService {

    private static final Logger logger = LoggerFactory.getLogger(NumberService.class);
    private final RestTemplate restTemplate;

    public NumberService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Integer> fetchNumbers(String qualifier) {
        String url = getUrlForQualifier(qualifier);
        logger.info("Fetching numbers from URL: {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ApiConstants.API_TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            logger.info("Received response: {}", response.getBody());
            return response.getBody() != null ? (List<Integer>) response.getBody().getOrDefault("numbers", Collections.emptyList()) : Collections.emptyList();
        } catch (Exception e) {
            logger.error("Error fetching numbers", e);
            return Collections.emptyList();
        }
    }

    private String getUrlForQualifier(String qualifier) {
        switch (qualifier) {
            case "p":
                return ApiConstants.PRIMES_URL;
            case "f":
                return ApiConstants.FIBONACCI_URL;
            case "e":
                return ApiConstants.EVEN_URL;
            case "r":
                return ApiConstants.RANDOM_URL;
            default:
                throw new IllegalArgumentException("Invalid qualifier");
        }
    }
}

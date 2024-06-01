package com.lari.topproductsservice.service;

import com.lari.topproductsservice.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://20.244.56.144/test/companies/%s/categories/%s/products";
    private final RestTemplate restTemplateWithAuth;

    public ProductService(@Qualifier("restTemplateWithAuth") RestTemplate restTemplateWithAuth) {
        this.restTemplateWithAuth = restTemplateWithAuth;
    }

    @Cacheable(value = "products", key = "#company + '_' + #category + '_' + #top + '_' + #minPrice + '_' + #maxPrice")
    public List<Product> fetchProducts(String company, String category, Integer top, Integer minPrice, Integer maxPrice) {
        String url = String.format(BASE_URL, company, category);
        String params = "?top=" + top;
        if (minPrice != null) {
            params += "&minPrice=" + minPrice;
        }
        if (maxPrice != null) {
            params += "&maxPrice=" + maxPrice;
        }
        Product[] products = restTemplateWithAuth.getForObject(url + params, Product[].class);
        if (products != null) {
            return List.of(products);
        } else {
            return new ArrayList<>();
        }
    }
}

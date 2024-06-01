package com.lari.topproductsservice.model;

import lombok.Data;

@Data
public class Product {
    private String productName;
    private double price;
    private double rating;
    private int discount;
    private String availability;
    private String company;
}

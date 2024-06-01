package com.lari.topproductsservice.model;

public class Product {
    private String productName;
    private double price;
    private double rating;
    private int discount;
    private String availability;

    public Product() {
    }

    public Product(String availability, int discount, double price, String productName, double rating) {
        this.availability = availability;
        this.discount = discount;
        this.price = price;
        this.productName = productName;
        this.rating = rating;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}


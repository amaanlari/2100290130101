package com.lari.topproductsservice.controller;

import com.lari.topproductsservice.model.Product;
import com.lari.topproductsservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories/{categoryname}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getTopProducts(
            @PathVariable String categoryname,
            @RequestParam(required = false) Integer top,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false, defaultValue = "1") Integer page) {

        String[] companies = {"AMZ", "FLP", "SNP", "WYN", "AZO"};
        List<Product> allProducts = new ArrayList<>();

        for (String company : companies) {
            allProducts.addAll(productService.fetchProducts(company, categoryname, top, minPrice, maxPrice));
        }

        // Sorting the products based on the sortBy and order parameters
        if (sortBy != null) {
            Comparator<Product> comparator = null;
            switch (sortBy) {
                case "price":
                    comparator = Comparator.comparing(Product::getPrice);
                    break;
                case "rating":
                    comparator = Comparator.comparing(Product::getRating);
                    break;
                case "discount":
                    comparator = Comparator.comparing(Product::getDiscount);
                    break;
                default:
                    break;
            }

            if (comparator != null && "desc".equals(order)) {
                comparator = comparator.reversed();
            }

            if (comparator != null) {
                allProducts.sort(comparator);
            }
        }

        // Pagination
        int itemsPerPage = (top != null) ? top : 10;
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, allProducts.size());

        return allProducts.subList(start, end);
    }

    @GetMapping("/{productid}")
    public Product getProductDetails(
            @PathVariable String categoryname,
            @PathVariable String productid) {

        String[] companies = {"AMZ", "FLP", "SNP", "WYN", "AZO"};

        for (String company : companies) {
            List<Product> products = productService.fetchProducts(company, categoryname, 100, null, null);
            for (Product product : products) {
                if (productid.equals(product.getProductName())) { // Assuming product name as ID for this example
                    product.setCompany(company);
                    return product;
                }
            }
        }
        return null;
    }
}

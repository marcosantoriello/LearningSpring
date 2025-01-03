package org.example.ch8_3.controllers;

import org.example.ch8_3.model.Product;
import org.example.ch8_3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductsController {

    private final ProductService productService;

    // Injecting the service bean
    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String viewProducts(Model model) {
        var products = productService.findAll();
        model.addAttribute("products", products);

        return "products";
    }

    @PostMapping(path = "/products")
    public String addProduct(
            @RequestParam String name,
            @RequestParam double price,
            Model model) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        productService.addProduct(p);

        var products = productService.findAll();
        model.addAttribute("products", products);

        return "products";
    }


}

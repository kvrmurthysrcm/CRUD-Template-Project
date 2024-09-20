package com.productapi.controller;

import com.productapi.dto.ProductDto;
import com.productapi.service.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class HomeController {
// http://localhost:9000/api/v1/ is the base URL

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(){
        return "Hello Home !!!";
    }

    @GetMapping("/error")
    public String error(){
        return "Unknown error occurred!!";
    }

    @GetMapping("/logout")
    public String logout(){
        return "Logout from app...!!";
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts(){
        List<ProductDto> products = productService.getAllProducts();
        System.out.println("@ getAllProducts()....." + products);
        return products;
        //return "Get all products !!!";
    }
}

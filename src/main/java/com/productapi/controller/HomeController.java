package com.productapi.controller;

import com.productapi.dto.ProductDto;
import com.productapi.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Get ALL Products
    @GetMapping("/products")
    public List<ProductDto> getAllProducts(){
        List<ProductDto> products = productService.getAllProducts();
        System.out.println("@ getAllProducts()....." + products);
        return products;
        //return "Get all products !!!";
    }

    // Create Product
    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) throws Exception {
        System.out.println("inside createEmployee() @ EmployeeController class.");
        ProductDto product = null;
        try {
            product = productService.createProduct(productDto);
            //redisService.setValue(emp.getId()+"", emp);
            System.out.println("HomeController: Created product: " + product);
        } catch(Exception e){
            System.out.println("HomeController: " + e.getMessage());
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            //         .body(e.getMessage());
            throw e;
        }
        return product;
    }

    // Delete the Product using product ID:
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteProduct(@PathVariable Long id){
        boolean deleted = false;
        deleted = productService.deleteEmployee(id);

        Map<String, Boolean> response = new HashMap();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    // Get Product using Product ID
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        ProductDto productDto = null;
        try {
            //productDto = (ProductDto) redisService.getValue(id + "");
            //if(productDto == null){
            productDto = productService.getProductById(id);
            //    redisService.setValue("" + productDto.getId(), productDto);
           // } else{
            //    System.out.println("Employee from cache: " + productDto);
           // }
        } catch(Exception e){
            System.out.println("Exception occurred: " + e.getMessage());
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.ok(productDto);
    }

    // Update product
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                    @RequestBody ProductDto productDto){
        ProductDto productDto1 = null;
        productDto1 = productService.updateProduct(id, productDto);
        //redisService.setValue("" + productDto.getId(), productDto); // update the cache also!
        return ResponseEntity.ok(productDto);
    }
}
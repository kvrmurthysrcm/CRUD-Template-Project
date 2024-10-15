package com.productapi.controller;

import com.productapi.dto.ProductDto;
import com.productapi.service.ProductService;
import com.productapi.service.TokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class HomeController {
// http://localhost:9000/api/v1/ is the base URL

    private final ProductService productService;
    private final TokenService tokenService;

    Logger logger = LoggerFactory.getLogger("HomeController.class");

    @Value("${application.hello}")
    private String greeting;

    public HomeController(ProductService productService, TokenService tokenService) {
        this.productService = productService;
        this.tokenService = tokenService;
    }

    @GetMapping("/")
    public String home(){
        return greeting;
    }

    @GetMapping("/error")
    public String error(){
        return "Unknown error occurred!!";
    }

    @GetMapping("/logout")
    public String logout(){
        return "Logout from app...!!";
    }

    @PostMapping("/token")
    public String token(Authentication authentication){
        logger.debug("Token requested for user: "+ authentication.getName() );
        String token = tokenService.generateToken(authentication);
        logger.debug("Token granted: "+ token);
        return token;
    }

    // Get ALL Products
    @GetMapping("/products")
    public List<ProductDto> getAllProducts(){
        List<ProductDto> products = productService.getAllProducts();
        System.out.println("logger level: " + logger.isDebugEnabled());
        System.out.println("logger level: " + logger);
        logger.debug("0. @ getAllProducts().....{}", products);
        logger.info("1. @ getAllProducts().....{}", products);
        logger.debug("2. @ getAllProducts().....{}", products);
        logger.warn("3. @ getAllProducts().....{}", products);
//        logger.error("4. @ getAllProducts().....{}", products);
//        logger.trace("5. @ getAllProducts().....{}", products);
        return products;
        //return "Get all products !!!";
    }

    // Create Product
    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) throws Exception {
        logger.debug("inside createEmployee() @ EmployeeController class.");
        ProductDto product = null;
        try {
            product = productService.createProduct(productDto);
            //redisService.setValue(emp.getId()+"", emp);
            logger.debug("HomeController: Created product: " + product);
        } catch(Exception e){
            logger.debug("HomeController: " + e.getMessage());
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
        logger.debug("@Controller:: Get product for ID: " + id) ;
        ProductDto productDto = null;
        try {
            productDto = productService.getProductById(id);
        } catch(Exception e){
           logger.debug("@Controller:: Exception occurred: " + e.getMessage());
            logger.debug("@Controller:: Exception occurred:productDto:: " + productDto);
            productDto = new ProductDto();
            productDto.setProductName("Error: "+e.getMessage());
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.ok(productDto);
    } // End of getProductById()....

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
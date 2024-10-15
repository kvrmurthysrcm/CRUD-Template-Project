package com.productapi.controller;

import com.productapi.dto.ProductDto;
import com.productapi.dto.UserDto;
import com.productapi.service.ProductService;
import com.productapi.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@Controller
//@RestController
@RequestMapping("/myui")
public class ThymeController {
    // http://localhost:9000/myui is the base URL

    private final ProductService productService;
    private final TokenService tokenService;

    Logger logger = LoggerFactory.getLogger("HomeController.class");

    @Value("${application.hello}")
    private String greeting;

    public ThymeController(ProductService productService, TokenService tokenService) {
        this.productService = productService;
        this.tokenService = tokenService;
    }

    // http://localhost:9000/myui/home
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("message", greeting);

        List<ProductDto> productDtos = productService.getAllProducts();
        model.addAttribute("productDtos", productDtos);

        ProductDto productDto1 = new ProductDto(100L, "watch", 3000.0, 2.0, 3.0);
        model.addAttribute("productDto1", productDto1);

        logger.debug("setting two model attributes...message and productDtos:: {}", productDtos);
         return "home";
    } // end of home()

    // http://localhost:9000/myui/products/productID
    @GetMapping("/products/{id}")
    public String productDetails(Model model, @PathVariable Long id){

        ProductDto productDto = null;
        try {
            productDto = productService.getProductById(id);
            model.addAttribute("productDto", productDto);
        } catch(Exception e){
            logger.debug("Exception occurred: " + e.getMessage());
        }
        logger.debug("attributes...message and productDto:: {}", productDto);
        return "productdetails";
    } //end of productDetails()

    // http://localhost:9000/myui/login
    @GetMapping("/login")
    public String login(Model model){
        logger.debug("Loading login page ...");
        UserDto userDto = new UserDto();
        // load empty userDto
        model.addAttribute("userDto", userDto);

        return "login";
    } // end of login()

    // http://localhost:9000/myui/login/save
    @PostMapping("/login/save")
    public String loginProcess(Model model,
                               @ModelAttribute("userDto") UserDto userDto){
        logger.debug("Verifying login page ...{}", userDto);

        // TODO::
        // get values from UI..validate it, process it..
        model.addAttribute("userDto", userDto);

        // forward to home page after successful login
        return "home";
    } // end of loginProcess()

    // http://localhost:9000/myui/register
    @GetMapping("/register")
    public String register(Model model){
        logger.debug("Loading register page ...");
        UserDto userDto = new UserDto();
        // load empty userDto
        model.addAttribute("userDto", userDto);

        return "register";
    } // end of register()


    // http://localhost:9000/myui/register/save
    @PostMapping("/register/save")
    public String registerSave(Model model,
                               @ModelAttribute("userDto") UserDto userDto){
        logger.debug("Saving registration data:: " + userDto);
        // TODO:
        // Save details to DB:

        // load login page with the succesfully registered data:
        model.addAttribute("userDto", userDto);

        return "login";
    } // end of registerSave()

}
package com.productapi.service;

import com.productapi.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    public List<ProductDto> getAllProducts();

    ProductDto createProduct(ProductDto productDto) throws Exception;

    boolean deleteEmployee(Long id);

    ProductDto getProductById(Long id);

    ProductDto updateProduct(Long id, ProductDto productDto);

    // TODO: Implement the following..
//    Page<ProductDto> getAllProductsUsingPagination(Pageable pageable);
//
//    Page<ProductDto> getAllProductsUsingPaginationList(PageRequestDto dto);
//
//    Page<ProductDto> getAllProductsUsingPaginationQueryMethod(PageRequestDto dto, String fName);
//
//    Page<ProductDto> getAllProductsUsingPaginationNative(PageRequestDto dto, String fName);

}
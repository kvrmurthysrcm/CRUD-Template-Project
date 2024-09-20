package com.productapi.service;

import com.productapi.dto.ProductDto;
import com.productapi.entity.ProductEntity;
import com.productapi.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        // use repository to fetch all Products..
        List<ProductEntity> entities = productRepository.findAll();
        System.out.println("@getAllProducts()..." + entities);
        if(entities == null){
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }// End of getAllProducts

    @Override
    @CachePut(value="products", key="#products.id")
    public ProductDto createProduct(ProductDto productDto) throws Exception {

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productDto, productEntity);
        productEntity = productRepository.save(productEntity);
        productDto.setId(productEntity.getId());
        return productDto;
    }

    @Override
    @CacheEvict(value="products", key="#id")
    public boolean deleteEmployee(Long id) {
        ProductEntity productEntity = productRepository.findById(id).get();
        if(productEntity != null)
            productRepository.delete(productEntity);
        else
            return false;
        return true;
    }

    @Override
    @CachePut(value="products", key="#products.id")
    public ProductDto getProductById(Long id) {
        ProductEntity productEntity
                = productRepository.findById(id).get();
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(productEntity, productDto);
        return productDto;
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        ProductEntity productEntity
                = productRepository.findById(id).get();

        productEntity.setId(productDto.getId());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setProductName(productDto.getProductName());
        productEntity.setVolume(productDto.getVolume());
        productEntity.setWight(productDto.getWight());

        productRepository.save(productEntity);
        return productDto;
    }

    private ProductDto toDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDto.class);
    }
}

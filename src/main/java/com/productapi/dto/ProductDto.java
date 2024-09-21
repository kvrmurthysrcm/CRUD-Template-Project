package com.productapi.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    Long id;

    String productName;

    Double price;

    Double wight;

    Double volume;
}
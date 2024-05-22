package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.ProductDto;
import com.lpdecastro.ecommerce.dto.ProductRequestDto;
import com.lpdecastro.ecommerce.dto.ProductsResponseDto;
import com.lpdecastro.ecommerce.dto.ResponseDto;
import com.lpdecastro.ecommerce.entity.ProductCategory;

public interface ProductService {

    ProductsResponseDto getProducts();
    ProductDto getProductById(long productId);
    ProductsResponseDto getProductsByCategory(ProductCategory category);
    ProductsResponseDto getProductsBySellerId(long sellerId);
    ProductDto addProduct(ProductRequestDto productRequestDto);
    ProductDto updateProduct(long productId, ProductRequestDto productRequestDto);
    ResponseDto deleteProductById(long productId);
}

package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;
import com.lpdecastro.ecommerce.entity.Product;
import com.lpdecastro.ecommerce.entity.ProductCategory;
import com.lpdecastro.ecommerce.entity.ProductView;
import com.lpdecastro.ecommerce.entity.Seller;
import com.lpdecastro.ecommerce.exception.AppException;
import com.lpdecastro.ecommerce.repository.CartItemRepository;
import com.lpdecastro.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.lpdecastro.ecommerce.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public ProductsResponseDto getProducts() {
        List<ProductViewDto> products = productRepository.getAll().stream().map(this::convertToDto).toList();

        return new ProductsResponseDto(products);
    }

    @Override
    public ProductDto getProductById(long productId) {
        return productRepository.findById(productId)
                .map(this::convertToDto)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductsResponseDto getProductsByCategory(ProductCategory category) {
        List<ProductViewDto> products = productRepository.getAllByCategory(category).stream()
                .map(this::convertToDto).toList();

        return new ProductsResponseDto(products);
    }

    @Override
    public ProductsResponseDto getProductsBySellerId(long sellerId) {
        List<ProductViewDto> products = productRepository.getAllBySellerId(sellerId).stream()
                .map(this::convertToDto).toList();

        return new ProductsResponseDto(products);
    }

    @Override
    public ProductDto addProduct(ProductRequestDto productRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Seller seller = (Seller) authentication.getPrincipal();

        Product product = convertToEntity(productRequestDto);
        product.setSeller(seller);

        return convertToDto(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(long productId, ProductRequestDto productRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Seller seller = (Seller) authentication.getPrincipal();

        Product currentProduct = productRepository.findByProductIdAndSeller_SellerId(productId, seller.getSellerId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));

        Product product = convertToEntity(productRequestDto);
        product.setProductId(currentProduct.getProductId());
        product.setCreatedAt(currentProduct.getCreatedAt());
        product.setSeller(currentProduct.getSeller());
        product.setUpdatedAt(LocalDateTime.now());

        return convertToDto(productRepository.save(product));
    }

    @Override
    public ResponseDto deleteProductById(long productId) {
        Product currentProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));

        cartItemRepository.findByProduct_ProductId(productId).ifPresent(cartItemRepository::delete);

        productRepository.delete(currentProduct);

        return new ResponseDto(true);
    }

    private ProductViewDto convertToDto(ProductView productView) {
        return modelMapper.map(productView, ProductViewDto.class);
    }

    private ProductDto convertToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    private Product convertToEntity(ProductRequestDto productRequestDto) {
        return modelMapper.map(productRequestDto, Product.class);
    }
}

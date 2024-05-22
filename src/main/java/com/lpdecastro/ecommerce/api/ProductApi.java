package com.lpdecastro.ecommerce.api;

import com.lpdecastro.ecommerce.dto.ProductDto;
import com.lpdecastro.ecommerce.dto.ProductRequestDto;
import com.lpdecastro.ecommerce.dto.ProductsResponseDto;
import com.lpdecastro.ecommerce.dto.ResponseDto;
import com.lpdecastro.ecommerce.entity.ProductCategory;
import com.lpdecastro.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/.rest/products")
@RequiredArgsConstructor
@Validated
public class ProductApi {

    private final ProductService productService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ProductsResponseDto getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    @PreAuthorize("permitAll()")
    public ProductDto getProductById(@PathVariable long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("permitAll()")
    public ProductsResponseDto getProductsByCategory(@NotNull @PathVariable ProductCategory category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/seller/{sellerId}")
    @PreAuthorize("permitAll()")
    public ProductsResponseDto getProductsBySellerId(@PathVariable long sellerId) {
        return productService.getProductsBySellerId(sellerId);
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ProductDto addProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.addProduct(productRequestDto);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ProductDto updateProduct(@PathVariable long productId,
                                    @Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.updateProduct(productId, productRequestDto);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseDto deleteProductById(@PathVariable long productId) {
        return productService.deleteProductById(productId);
    }
}

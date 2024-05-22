package com.lpdecastro.ecommerce.api;

import com.lpdecastro.ecommerce.dto.*;
import com.lpdecastro.ecommerce.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/.rest/sellers")
@RequiredArgsConstructor
public class SellerApi {

    private final SellerService sellerService;

    @GetMapping("/current")
    @PreAuthorize("hasRole('SELLER')")
    public SellerDto getCurrentSeller() {
        return sellerService.getCurrentSeller();
    }

    @GetMapping("/{sellerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public SellerDto getSellerById(@PathVariable long sellerId) {
        return sellerService.getSellerById(sellerId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SellersResponseDto getSellers() {
        return sellerService.getSellers();
    }

    @PutMapping("/current")
    @PreAuthorize("hasRole('SELLER')")
    public SellerDto updateCurrentSeller(@Valid @RequestBody SellerRequestDto sellerRequestDto) {
        return sellerService.updateCurrentSeller(sellerRequestDto);
    }

    @PutMapping("/current/password")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseDto updateCurrentSellerPassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        return sellerService.updateCurrentSellerPassword(changePasswordRequestDto);
    }

    @DeleteMapping("/{sellerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto deleteSellerById(@PathVariable long sellerId) {
        return sellerService.deleteSellerById(sellerId);
    }
}

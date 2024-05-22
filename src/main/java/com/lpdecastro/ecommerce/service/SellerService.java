package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;

public interface SellerService {

    SellerDto getCurrentSeller();
    SellerDto getSellerById(long sellerId);
    SellersResponseDto getSellers();
    SellerDto updateCurrentSeller(SellerRequestDto sellerRequestDto);
    ResponseDto updateCurrentSellerPassword(ChangePasswordRequestDto changePasswordRequestDto);
    ResponseDto deleteSellerById(long sellerId);
}

package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;
import com.lpdecastro.ecommerce.entity.Seller;
import com.lpdecastro.ecommerce.exception.AppException;
import com.lpdecastro.ecommerce.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.lpdecastro.ecommerce.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SellerDto getCurrentSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Seller seller = (Seller) authentication.getPrincipal();

        return convertToDto(seller);
    }

    @Override
    public SellerDto getSellerById(long sellerId) {
        return sellerRepository.findById(sellerId)
                .map(this::convertToDto)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
    }

    @Override
    public SellersResponseDto getSellers() {
        List<SellerDto> sellers = sellerRepository.findAll().stream().map(this::convertToDto).toList();

        return new SellersResponseDto(sellers);
    }

    @Override
    public SellerDto updateCurrentSeller(SellerRequestDto sellerRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Seller currentSeller = (Seller) authentication.getPrincipal();

        validateSellerAttributes(sellerRequestDto.getMobile(), sellerRequestDto.getEmail(),
                sellerRequestDto.getUsername(), currentSeller.getSellerId());

        Seller seller = convertToEntity(sellerRequestDto);
        seller.setSellerId(currentSeller.getSellerId());
        seller.setPassword(currentSeller.getPassword());
        seller.setCreatedAt(currentSeller.getCreatedAt());
        seller.setRole(currentSeller.getRole());

        return convertToDto(sellerRepository.save(seller));
    }

    @Override
    public ResponseDto updateCurrentSellerPassword(ChangePasswordRequestDto changePasswordRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Seller currentSeller = (Seller) authentication.getPrincipal();

        validateSellerPassword(changePasswordRequestDto.getCurrentPassword(), currentSeller.getPassword());

        currentSeller.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));

        sellerRepository.save(currentSeller);

        return new ResponseDto(true);
    }

    @Override
    public ResponseDto deleteSellerById(long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));

        sellerRepository.delete(seller);

        return new ResponseDto(true);
    }

    private void validateSellerAttributes(String mobile, String email, String username, long sellerId) {
        if (StringUtils.hasText(mobile) && sellerRepository.existsByMobileAndSellerIdNot(mobile, sellerId)) {
            throw new AppException(HttpStatus.CONFLICT, MOBILE_ALREADY_EXISTS);
        }
        if (StringUtils.hasText(email) && sellerRepository.existsByEmailAndSellerIdNot(email, sellerId)) {
            throw new AppException(HttpStatus.CONFLICT, EMAIL_ALREADY_EXISTS);
        }
        if (StringUtils.hasText(username) && sellerRepository.existsByUsernameAndSellerIdNot(username, sellerId)) {
            throw new AppException(HttpStatus.CONFLICT, USERNAME_ALREADY_EXISTS);
        }
    }

    private void validateSellerPassword(String currentPassword, String existingEncodedPassword) {
        if (!passwordEncoder.matches(currentPassword, existingEncodedPassword)) {
            throw new AppException(HttpStatus.BAD_REQUEST, CURRENT_PASSWORD_IS_INCORRECT);
        }
    }

    private SellerDto convertToDto(Seller seller) {
        return modelMapper.map(seller, SellerDto.class);
    }

    private Seller convertToEntity(SellerRequestDto sellerRequestDto) {
        return modelMapper.map(sellerRequestDto, Seller.class);
    }
}

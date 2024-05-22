package com.lpdecastro.ecommerce.service;

import com.lpdecastro.ecommerce.dto.*;
import com.lpdecastro.ecommerce.entity.*;
import com.lpdecastro.ecommerce.exception.AppException;
import com.lpdecastro.ecommerce.repository.AddressRepository;
import com.lpdecastro.ecommerce.repository.CreditCardRepository;
import com.lpdecastro.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.lpdecastro.ecommerce.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CreditCardRepository creditCardRepository;
    private final AddressRepository addressRepository;

    @Override
    public CustomerDto getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Customer currentCustomer = (Customer) authentication.getPrincipal();

        return convertToDto(currentCustomer);
    }

    @Override
    public CustomerDto getCustomerById(long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));

        return convertToDto(customer);
    }

    @Override
    public CustomersResponseDto getCustomers() {
        List<CustomerViewDto> customers = customerRepository.getAll().stream().map(this::convertToDto).toList();

        return new CustomersResponseDto(customers);
    }

    @Override
    @Transactional
    public CustomerDto updateCurrentCustomer(CustomerRequestDto customerRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Customer currentCustomer = (Customer) authentication.getPrincipal();

        validateCustomerAttributes(customerRequestDto.getMobile(), customerRequestDto.getEmail(),
                customerRequestDto.getUsername(), currentCustomer.getCustomerId());

        Customer customer = convertToEntity(customerRequestDto);
        customer.setCustomerId(currentCustomer.getCustomerId());
        customer.setPassword(currentCustomer.getPassword());
        customer.setAddresses(currentCustomer.getAddresses());
        customer.setCreditCards(currentCustomer.getCreditCards());
        customer.setRole(currentCustomer.getRole());
        customer.setCreatedAt(currentCustomer.getCreatedAt());
        customer.setUpdatedAt(currentCustomer.getUpdatedAt());
        customer.setCart(currentCustomer.getCart());

        return convertToDto(customerRepository.save(customer));
    }

    @Override
    public ResponseDto updateCurrentCustomerPassword(ChangePasswordRequestDto changePasswordRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Customer currentCustomer = (Customer) authentication.getPrincipal();

        validateCustomerPassword(changePasswordRequestDto.getCurrentPassword(), currentCustomer.getPassword());

        currentCustomer.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        currentCustomer.setUpdatedAt(LocalDateTime.now());

        customerRepository.save(currentCustomer);

        return new ResponseDto(true);
    }

    @Override
    public AddressDto updateCurrentCustomerAddress(AddressType type, AddressRequestDto addressRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Customer currentCustomer = (Customer) authentication.getPrincipal();

        Address currentAddress = currentCustomer.getAddresses().stream()
                .filter(address -> type == address.getType()).findAny()
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, ADDRESS_NOT_FOUND));

        Address address = convertToEntity(addressRequestDto);
        address.setAddressId(currentAddress.getAddressId());
        address.setType(type);

        return convertToDto(addressRepository.save(address));
    }

    @Override
    public CreditCardDto updateCurrentCustomerCreditCard(long creditCardId, CreditCardRequestDto creditCardRequestDto) {
        CreditCard currentCreditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CREDIT_CARD_NOT_FOUND));

        CreditCard creditCard = convertToEntity(creditCardRequestDto);
        creditCard.setCreditCardId(currentCreditCard.getCreditCardId());

        return convertToDto(creditCardRepository.save(creditCard));
    }

    @Override
    public ResponseDto deleteCurrentCustomerAddress(AddressType type) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Customer currentCustomer = (Customer) authentication.getPrincipal();

        Address currentAddress = currentCustomer.getAddresses().stream()
                .filter(address -> type == address.getType()).findAny()
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, ADDRESS_NOT_FOUND));

        addressRepository.delete(currentAddress);

        return new ResponseDto(true);
    }

    @Override
    public ResponseDto deleteCurrentCustomerCreditCard(long creditCardId) {
        CreditCard currentCreditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CREDIT_CARD_NOT_FOUND));

        creditCardRepository.delete(currentCreditCard);

        return new ResponseDto(true);
    }

    @Override
    public ResponseDto deleteCustomerById(long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));

        customerRepository.delete(customer);

        return new ResponseDto(true);
    }

    private void validateCustomerAttributes(String mobile, String email, String username, long customerId) {
        if (StringUtils.hasText(mobile) && customerRepository.existsByMobileAndCustomerIdNot(mobile, customerId)) {
            throw new AppException(HttpStatus.CONFLICT, MOBILE_ALREADY_EXISTS);
        }
        if (StringUtils.hasText(email) && customerRepository.existsByEmailAndCustomerIdNot(email, customerId)) {
            throw new AppException(HttpStatus.CONFLICT, EMAIL_ALREADY_EXISTS);
        }
        if (StringUtils.hasText(username) && customerRepository.existsByUsernameAndCustomerIdNot(username, customerId)) {
            throw new AppException(HttpStatus.CONFLICT, USERNAME_ALREADY_EXISTS);
        }
    }

    private void validateCustomerPassword(String currentPassword, String existingEncodedPassword) {
        if (!passwordEncoder.matches(currentPassword, existingEncodedPassword)) {
            throw new AppException(HttpStatus.BAD_REQUEST, CURRENT_PASSWORD_IS_INCORRECT);
        }
    }

    private Customer convertToEntity(CustomerRequestDto customerRequestDto) {
        return modelMapper.map(customerRequestDto, Customer.class);
    }

    private Address convertToEntity(AddressRequestDto addressRequestDto) {
        return modelMapper.map(addressRequestDto, Address.class);
    }

    private CreditCard convertToEntity(CreditCardRequestDto creditCardRequestDto) {
        return modelMapper.map(creditCardRequestDto, CreditCard.class);
    }

    private CustomerDto convertToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    private CustomerViewDto convertToDto(CustomerView customerView) {
        return modelMapper.map(customerView, CustomerViewDto.class);
    }

    private AddressDto convertToDto(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    private CreditCardDto convertToDto(CreditCard creditCard) {
        return modelMapper.map(creditCard, CreditCardDto.class);
    }
}

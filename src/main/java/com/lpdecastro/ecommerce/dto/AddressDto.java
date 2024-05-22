package com.lpdecastro.ecommerce.dto;

import lombok.Data;

@Data
public class AddressDto {

    private long addressId;
    private String type;
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String zipCode;
}

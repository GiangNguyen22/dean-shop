package com.mr.deanshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {
    private UUID id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
}

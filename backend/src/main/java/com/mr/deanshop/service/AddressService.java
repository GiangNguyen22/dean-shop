package com.mr.deanshop.service;

import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.dto.AddressRequest;
import com.mr.deanshop.entity.Address;
import com.mr.deanshop.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    public Address createAddress(AddressRequest addressRequest, Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Address address = Address.builder()
                .name(addressRequest.getName())
                .street(addressRequest.getStreet())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .phone(addressRequest.getPhone())
                .zipcode(addressRequest.getZipCode())
                .user(user)
                .build();
        return addressRepository.save(address);
    }
}

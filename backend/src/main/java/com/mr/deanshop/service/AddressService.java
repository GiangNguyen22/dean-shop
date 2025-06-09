package com.mr.deanshop.service;

import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.dto.AddressRequest;
import com.mr.deanshop.entity.Address;
import com.mr.deanshop.exceptions.ResourceNotFoundEx;
import com.mr.deanshop.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

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
                .zipCode(addressRequest.getZipCode())
                .user(user)
                .build();
        return addressRepository.save(address);
    }

    public void deleteAddress(UUID id) {
        addressRepository.deleteById(id);
    }

    public Address updateAddress(AddressRequest addressRequest, UUID id,Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Address address = addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEx("No address found with id: " + id));
        address.setName(addressRequest.getName());
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setPhone(addressRequest.getPhone());
        address.setZipCode(addressRequest.getZipCode());
        address.setUser(user);

        return addressRepository.save(address);
    }
}

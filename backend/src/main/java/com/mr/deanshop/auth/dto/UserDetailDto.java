package com.mr.deanshop.auth.dto;


import com.mr.deanshop.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailDto {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private Object authorityList;
    private List<Address> addressList;
}

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
public class ProductResourceDto {
    private UUID id;
    private String name;
    private String url;
    private String type;
    private boolean isPrimary;
}

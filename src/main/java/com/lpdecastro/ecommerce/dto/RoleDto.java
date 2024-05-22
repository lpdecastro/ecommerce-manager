package com.lpdecastro.ecommerce.dto;

import com.lpdecastro.ecommerce.entity.RoleName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleDto {

    private long roleId;
    private RoleName name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.lpdecastro.ecommerce.dto;

import com.lpdecastro.ecommerce.validation.PasswordMatches;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordMatches
public class ChangePasswordRequestDto {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String newPasswordConfirm;
}

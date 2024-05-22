package com.lpdecastro.ecommerce.validation;

import com.lpdecastro.ecommerce.dto.ChangePasswordRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, ChangePasswordRequestDto> {

    @Override
    public boolean isValid(ChangePasswordRequestDto requestDto, ConstraintValidatorContext context) {
        return requestDto.getNewPassword() != null
                && requestDto.getNewPassword().equals(requestDto.getNewPasswordConfirm());
    }
}

package com.petproject.youtubeclone.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * Validator for the @ValidImageType annotation.
 */
public class ImageTypeValidator implements ConstraintValidator<ValidImageType, MultipartFile> {
    @Override
    public void initialize(ValidImageType constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // You can choose to return false if you want to consider empty files as invalid
        }
        String contentType = file.getContentType();
        return "image/jpeg".equals(contentType) || "image/png".equals(contentType);
    }
}

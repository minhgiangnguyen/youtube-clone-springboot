package com.petproject.youtubeclone.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates if the provided file is of an allowed image type (.jpg, .jpeg, .png).
 */
@Constraint(validatedBy = ImageTypeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })

@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageType {
    String message() default "Invalid image type. Allowed types are .jpg, .jpeg, .png.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


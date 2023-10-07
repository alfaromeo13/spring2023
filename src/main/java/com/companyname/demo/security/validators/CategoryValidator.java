package com.companyname.demo.security.validators;

import com.companyname.demo.dto.CategoryDTO;
import com.companyname.demo.errors.ValidationException;
import com.companyname.demo.services.CategoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CategoryValidator implements Validator {

    private final CategoryService categoryService;

    @Override //which instance can be validated
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CategoryDTO.class);
    }


    @Override
    @SneakyThrows //target object must be an instance of CategoryDTO.class
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        CategoryDTO categoryDTO = (CategoryDTO) target;
        // validation logic should be done for each field of class; however,
        // we already have field validators inside dto class, hence we don't
        // have to check if the field is null or passed and similar
        //so just custom logic...
        validateName(categoryDTO.getName(), errors);

        if (errors.hasErrors()) throw new ValidationException(errors);
    }

    private void validateName(String name, Errors errors) {
        if (categoryService.existsByName(name)) {
            errors.rejectValue(
                    "name",
                    "name.error", //used for front-end translation
                    "Category already exists"
            );
        }
    }
}

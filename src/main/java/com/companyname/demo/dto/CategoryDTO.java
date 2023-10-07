package com.companyname.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CategoryDTO {
    private Integer id;
    @NotNull //required
//    @NotEmpty //can't be empty string
    @NotBlank// "    "  we replace empty with blank because black checks for empty
    @Size(min = 5, max = 22) //size of string and can only be used on strings!
    //@Min() | @Max() because with above one we don't know what caused an error
    private String name;

    @Email //regular expression which checks if this field contains email
//    @Pattern(regexp = "")//on pattern, we can write our custom regular expression
    private String email;

    //IN EACH constructor of validation annotations, we can pass message param

    @NotNull(message = "Description is required")
    @NotEmpty(message = "Desc. is empty")
    @NotBlank(message = "Message is blank")
    private String description;
}
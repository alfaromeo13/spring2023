package com.companyname.demo.mappers;

import com.companyname.demo.dto.CategoryDTO;
import com.companyname.demo.entities.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // componentModel = "spring" means that the interface should become
    // part of spring bean container meaning implementation will be created
    List<CategoryDTO> toDTOList(List<Category> categories);

    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);
}
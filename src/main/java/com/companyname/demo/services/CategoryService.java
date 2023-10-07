package com.companyname.demo.services;

import com.companyname.demo.dto.CategoryDTO;
import com.companyname.demo.mappers.CategoryMapper;
import com.companyname.demo.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        return mapper.toDTOList(categoryRepository.findAll());
    }

    public void save(CategoryDTO categoryDTO) {
        categoryRepository.save(mapper.toEntity(categoryDTO));
    }

    public void update(Integer id, CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        categoryRepository.save(mapper.toEntity(categoryDTO)); //SELECT + UPDATE
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);//SELECT + DELETE
    }
}

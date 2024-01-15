package com.companyname.demo.services;

import com.companyname.demo.dto.CategoryDTO;
import com.companyname.demo.mappers.CategoryMapper;
import com.companyname.demo.repositories.CategoryReadRepository;
import com.companyname.demo.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;

    //demo for master-slave architecture
    private final CategoryRepository categoryRepository;
    private final CategoryReadRepository categoryReadRepository;

    public List<CategoryDTO> findAll(String type) {
          return ("master".equals(type)) ?
                    mapper.toDTOList(categoryRepository.findAll()) :
                    mapper.toDTOList(categoryReadRepository.findAll());
    }
    public void save(CategoryDTO categoryDTO, String type) {
        if ("master".equals(type))
            categoryRepository.save(mapper.toEntity(categoryDTO));
        else categoryReadRepository.save(mapper.toEntity(categoryDTO));
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public void update(Integer id, CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        if(categoryRepository.existsById(id)) categoryRepository.save(mapper.toEntity(categoryDTO)); //SELECT + UPDATE
        if(categoryReadRepository.existsById(id)) categoryReadRepository.save(mapper.toEntity(categoryDTO));
    }

    public void delete(Integer id) {
        if(categoryRepository.existsById(id)) categoryRepository.deleteById(id);//SELECT + DELETE
        if(categoryReadRepository.existsById(id)) categoryReadRepository.deleteById(id);
    }

    public CategoryDTO findById(Integer id, String type) {
        return ("master".equals(type)) ?
                mapper.toDTO(categoryRepository.findById(id).orElse(null)) :
                mapper.toDTO(categoryReadRepository.findById(id).orElse(null));
    }
}

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

          if("master".equals(type)){
              log.info("VRACE PODATKE IZ MASTERA");
              return mapper.toDTOList(categoryRepository.findAll());
          }else{
              log.info("VRACE PODATKE IZ SLAVEA");
              return mapper.toDTOList(categoryReadRepository.findAll());
          }
    }
    public void save(CategoryDTO categoryDTO, String type) {
        if ("master".equals(type)) {
            log.info("CUVA GA U MASTER");
            categoryRepository.save(mapper.toEntity(categoryDTO));
        } else {
            log.info("CUVA GA U SLAVE");
            categoryReadRepository.save(mapper.toEntity(categoryDTO));
        }
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public void update(Integer id, CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        if(categoryRepository.existsById(id)) {
            log.info("POSTOJI U MATSER I UPDEJTUJE GA....");
            categoryRepository.save(mapper.toEntity(categoryDTO)); //SELECT + UPDATE
        }
        if(categoryReadRepository.existsById(id)) {
            log.info("POSTOJI U SLAVE I UPDEJTUJE GA....");
            categoryReadRepository.save(mapper.toEntity(categoryDTO));
        }
    }

    public void delete(Integer id) {
        if(categoryRepository.existsById(id)) {
            log.info("POSTOJI U MASTER I BRISE GA....");
            categoryRepository.deleteById(id);//SELECT + DELETE
        }
        if(categoryReadRepository.existsById(id)){
            log.info("POSTOJI U SLAVE I BRISE GA....");
            categoryReadRepository.deleteById(id);
        }
    }

    public CategoryDTO findById(Integer id, String type) {
        return ("master".equals(type)) ?
                mapper.toDTO(categoryRepository.findById(id).orElse(null)) :
                mapper.toDTO(categoryReadRepository.findById(id).orElse(null));
    }
}

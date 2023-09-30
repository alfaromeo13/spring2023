package com.companyname.demo.services;

import com.companyname.demo.dto.ProductDTO;
import com.companyname.demo.entities.Product;
import com.companyname.demo.mappers.ProductMapper;
import com.companyname.demo.repositories.ProductRepository;
import com.companyname.demo.search.ProductSearchFilter;
import com.companyname.demo.specs.ProductSearchSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper mapper;
    private final ProductRepository productRepository;
    private final ProductSearchSpecification specification;

    public List<ProductDTO> filter(ProductSearchFilter filter, Pageable pageable) {
        specification.setProductSearchFilter(filter);
        //calls toPredicate() and generates WHERE clause which is added to final query to DB
        return mapper.toDTOList(productRepository.findAll(specification, pageable).getContent());
    }

    public void save(ProductDTO productDTO) { //we simulate INSERT operation
        Product product = mapper.toEntity(productDTO);
        productRepository.save(product);//this sets id of 'product' field
        log.info("new generated id: {}", product.getId());
        log.info("exists : {}", productRepository.existsById(product.getId()));
    }

    @Transactional
    public void findWithTransaction(Integer id) {
        //functional way to check whether product is present or not
        productRepository.getReferenceById(22);
        productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        productRepository.findById(id); //still we will have just one query
        productRepository.findById(id);
    }
}
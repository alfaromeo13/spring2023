package com.companyname.demo.services;

import com.companyname.demo.dto.ProductDTO;
import com.companyname.demo.mappers.ProductMapper;
import com.companyname.demo.repositories.ProductRepository;
import com.companyname.demo.search.ProductSearchFilter;
import com.companyname.demo.specs.ProductSearchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
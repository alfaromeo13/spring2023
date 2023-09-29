package com.companyname.demo.mappers;

import com.companyname.demo.dto.ProductDTO;
import com.companyname.demo.entities.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    List<ProductDTO> toDTOList(List<Product> product);
}

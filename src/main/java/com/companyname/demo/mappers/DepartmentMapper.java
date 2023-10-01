package com.companyname.demo.mappers;

import com.companyname.demo.dto.DepartmentDTO;
import com.companyname.demo.entities.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    List<DepartmentDTO> toDTOList(List<Department> departments);
}

package com.companyname.demo.services;

import com.companyname.demo.dto.DepartmentDTO;
import com.companyname.demo.entities.Department;
import com.companyname.demo.mappers.DepartmentMapper;
import com.companyname.demo.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentMapper mapper;
    private final DepartmentRepository departmentRepository;

    public Page<Department> findByNameWithoutUsers(String name, Pageable pageable) {
        return departmentRepository.findByNameWithoutUsers(name, pageable);
    }

    public Page<Department> findByNameWithUsers(String name, Pageable pageable) {
        return departmentRepository.findByNameWithUsers(name, pageable);
    }

    public List<DepartmentDTO> findByNameWithUsersBestWay(String name, Pageable pageable) {
        Page<Integer> ids = departmentRepository.findIdsByName(name, pageable);
        List<Department> departments = departmentRepository.findByIdsWithUsers(ids.getContent());
        return mapper.toDTOList(departments); //🤪
    }
}
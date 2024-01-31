package com.companyname.demo.repositories;

import com.companyname.demo.annotations.DataSource;
import com.companyname.demo.entities.Category;
import com.companyname.demo.enums.DataSourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryReadRepository extends JpaRepository<Category,Integer> {
    @DataSource(DataSourceType.SLAVE)
    Category save(Category category);

    @DataSource(DataSourceType.SLAVE)
    List<Category> findAll();

    @DataSource(DataSourceType.SLAVE)
    Category findById(long id);

    @DataSource(DataSourceType.SLAVE)
    List<Category> findByNameStartingWith(String title);
}
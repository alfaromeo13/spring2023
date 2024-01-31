package com.companyname.demo.repositories;

import com.companyname.demo.annotations.DataSource;
import com.companyname.demo.entities.Category;
import com.companyname.demo.enums.DataSourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // this does this:
    // select count(*) from Category where category.name = ?1
    boolean existsByName(String name);
    @DataSource(DataSourceType.MASTER)
    List<Category> findAll();

    @Query(value = """
            select category from Category category
            join fetch category.products where category.id = :id
            """)
    Category findByIdWithProducts(@Param("id") Integer id);
}
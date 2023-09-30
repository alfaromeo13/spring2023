package com.companyname.demo.repositories;

import com.companyname.demo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = """
            select category from Category category
            join fetch category.products where category.id = :id
            """)
    Category findByIdWithProducts(@Param("id") Integer id);
}

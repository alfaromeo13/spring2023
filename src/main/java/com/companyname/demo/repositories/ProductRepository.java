package com.companyname.demo.repositories;

import com.companyname.demo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    //~example how to update entity with JPQL
    @Modifying
    @Query(value = "update Product product set product.title = :title where product.id = :id")
    void updateProductTitle(@Param("id") Integer id, @Param("title") String title);

    @Modifying
    @Query(value = "delete from Product product where product.id = ?1")
    void deleteProductById(Integer id);
}
package com.companyname.demo.Repositories;

import com.companyname.demo.DemoApplication;
import com.companyname.demo.entities.Category;
import com.companyname.demo.entities.Product;
import com.companyname.demo.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@Slf4j
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void cascadeDeleteTest() {
        categoryRepository.deleteById(2);
    }

    @Test
    void cascadeUpdateTest() {
        Category category = categoryRepository.findById(3).orElseThrow(EntityNotFoundException::new);
        category.setName("updated name 1");
// -> example for orphanRemoval = true  category.setProducts(Collections.emptyList());
        categoryRepository.save(category);
    }

    @Test
    void appendNewProductTest() {
        Category category = categoryRepository.findByIdWithProducts(3);

        Product product = new Product();
        product.setTitle("Coca cola");
        product.setShortDescription("Some description...");
        product.setPrice(BigDecimal.valueOf(22.3));
        product.setCategory(category);

        category.addProduct(product);
        categoryRepository.save(category);
    }

    @Test
    void removeSomeProductTest() {
        Category category = categoryRepository.findByIdWithProducts(5);
        category.removeProduct(12);
        //this will remove it because of orphanRemoval = true
        categoryRepository.save(category);
    }
}
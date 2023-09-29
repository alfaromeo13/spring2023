package com.companyname.demo.specs;

import com.companyname.demo.entities.Category;
import com.companyname.demo.entities.Product;
import com.companyname.demo.search.ProductSearchFilter;
import jakarta.persistence.criteria.*;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Setter
@Component
public class ProductSearchSpecification implements Specification<Product> {

    private ProductSearchFilter productSearchFilter; //passed search filters

    @Override //predicates => dynamic where clause (depends on params which are passed to the specification)
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        //CriteriaBuilder is used for making WHERE cases
        //CriteriaQuery<?> is used for making sub queries,group by,distinct,... (PODUPITI)
        //all of this can be done by criteria query

        //we write our filter logic here (now we see why using wrapper class is better than primitives)
        List<Predicate> predicateList = new ArrayList<>();

        //'category' is field name in Product entity
        //default JoinType.INNER which means if category doesn't exist we won't get row about Product
        //if we want to get it even though it might not exist we use JoinType.LEFT
        Join<Product, Category> categoryJoin = root.join("category"); //like this we will always do join!
        //if we need a Category alongside with Product(since it is LAZY relation we can write this)

        //Pagination issues SOLVED
        if (query.getResultType() != Long.class) {
            root.fetch("category");
        }

        //filter by title
        if (StringUtils.isNotBlank(productSearchFilter.getTitle())) {//checks 1)null 2)"" 3)" "
            Predicate titlePredicate = criteriaBuilder.equal(root.get("title"), productSearchFilter.getTitle());
            predicateList.add(titlePredicate);
        }

        //filter by short description
        String shortDesc = productSearchFilter.getShortDescription();
        if (StringUtils.isNotBlank(shortDesc)) {
            Predicate shortDescPredicate = criteriaBuilder.like(root.get("shortDescription"), "%" + shortDesc + "%");
            predicateList.add(shortDescPredicate);
        }

        //filter by join example
        if (StringUtils.isNotBlank(productSearchFilter.getCategoryName())) {
            //"name" used below is field from Category entity
            predicateList.add(criteriaBuilder.equal(categoryJoin.get("name"), productSearchFilter.getCategoryName()));
        }

        //'and' connects all predicates with word 'and' between all of them
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
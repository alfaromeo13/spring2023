package com.companyname.demo.repositories;

import com.companyname.demo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = """
            select orderr from Order orderr
            join fetch orderr.items
            where orderr.id = ?1
            """)
    Order findWithItems(Integer id);
}
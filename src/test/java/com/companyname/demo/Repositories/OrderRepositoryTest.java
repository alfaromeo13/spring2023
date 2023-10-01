package com.companyname.demo.Repositories;

import com.companyname.demo.DemoApplication;
import com.companyname.demo.entities.Customer;
import com.companyname.demo.entities.Order;
import com.companyname.demo.entities.OrderItem;
import com.companyname.demo.entities.Product;
import com.companyname.demo.enums.OrderStatusEnum;
import com.companyname.demo.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void createOrderWithMultipleItems() {
        Customer customer = new Customer();
        customer.setId(3L);

        Order order = new Order();
        order.setStatus(OrderStatusEnum.IN_PROGRESS);
        order.setTotalPrice(BigDecimal.valueOf(22.5));
        order.setCustomer(customer);

        Product cocaCola = new Product();
        cocaCola.setId(11);

        Product pepsi = new Product();
        pepsi.setId(15);

        Product zero = new Product();
        zero.setId(16);

        OrderItem item1 = new OrderItem();
        item1.setOrder(order);
        item1.setProduct(cocaCola);
        item1.setQuantity(10);

        OrderItem item2 = new OrderItem();
        item2.setOrder(order);
        item2.setProduct(zero);
        item2.setQuantity(3);

        OrderItem item3 = new OrderItem();
        item3.setOrder(order);
        item3.setProduct(pepsi);
        item3.setQuantity(4);

        order.addItem(List.of(item1, item2, item3));

        Order persistedOrder = orderRepository.save(order);
        assertThat(persistedOrder).isNotNull();
        assertThat(persistedOrder.getItems()).hasSize(3);
    }

    @Test
    void updateItemOnExistingOrder() {
        Order order = orderRepository.findWithItems(1);
        //update
        order.getItems().get(0).setQuantity(99);

        Product prod = new Product();
        prod.setId(17);

        //we add new item
        OrderItem newItem = new OrderItem();
        newItem.setOrder(order);
        newItem.setProduct(prod);
        newItem.setQuantity(-1);

        order.addItem(newItem);

        orderRepository.save(order);
    }

    @Test
    void deleteItemOnExistingOrder() {
        Order order = orderRepository.findWithItems(1);
        List<OrderItem> items = order.getItems();
        items.remove(items.size() - 1);
        orderRepository.save(order);
    }

    @Test
    void changeOrderStatus() {
        Order order = orderRepository.findWithItems(2);
        order.setStatus(OrderStatusEnum.DONE);
        Order updatedOrder = orderRepository.save(order);
        assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatusEnum.DONE);
    }

    @Test
    void deleteOrderWithOrderItems() {
        orderRepository.deleteById(3);
        assertThat(orderRepository.findById(3)).isEmpty();
    }
}
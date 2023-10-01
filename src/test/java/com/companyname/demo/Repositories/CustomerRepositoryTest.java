package com.companyname.demo.Repositories;

import com.companyname.demo.DemoApplication;
import com.companyname.demo.entities.Customer;
import com.companyname.demo.enums.CustomerTypeEnum;
import com.companyname.demo.repositories.CustomerRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void customerCreateTest() {
        Customer customer = new Customer();
        customer.setFullName("Jovan Vukovic");
        customer.setType(CustomerTypeEnum.INDIVIDUAL);
        customerRepository.save(customer);

        Customer persistedCustomer = customerRepository.findByFullName("Jovan Vukovic");
        assertThat(persistedCustomer).isNotNull();
    }

    @Test
    void customerUpdateTest() {
        Customer customer = customerRepository.findById(1L).orElseThrow(EntityExistsException::new);
        customer.setType(CustomerTypeEnum.LEGAL);
        customer.setFullName("Jovan Vukovicc");
        customerRepository.save(customer);
    }

    @Test
    void customerDeleteTest() { //cannot delete user if we have reference
        customerRepository.deleteById(3L);
    }
}

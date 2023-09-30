package com.companyname.demo.Repositories;

import com.companyname.demo.DemoApplication;
import com.companyname.demo.entities.User;
import com.companyname.demo.projections.UserFirstAndLastNameProjection;
import com.companyname.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

// while running tests spring bean container will be run
// this also means in tests we can inject our beans! :D
@Slf4j
@ActiveProfiles("dev") // profile which will be used for our test
@ExtendWith(SpringExtension.class)// we use JUnit5 tests like this
@SpringBootTest(classes = DemoApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    //test methods don't have return type!
    @Test
    void test1() {
        log.info("exists - > {}", userRepository.existsByUsername("jovan"));
    }

    @Test
    void findUserNameInDepartmentIdTuple() {
        List<Tuple> tuples = userRepository.findUserNameInDepartmentIdTuple(1);
        tuples.forEach(tuple -> log.info("{} {}", tuple.get("firstName"), tuple.get("lastName")));
    }

    @Test
    void findUserNameInDepartmentIdUsingProjection() {
        List<UserFirstAndLastNameProjection> projection = userRepository.findUserNameInDepartmentIdCustomProjection(1);
        projection.forEach(x -> log.info("{} {}", x.getFirstName(), x.getLastName()));
    }

    @Test
    void usingRecords() {
        userRepository.getByDepartmentIdWithRecord(1)
                .forEach(r -> log.info("{} {}", r.firstName(), r.lastName()));
    }

    @Test
    void findUser() {
        Optional<User> optionalUser = userRepository.findById(1L);
        //if row doesn't exist exception will be thrown
        User user = optionalUser.orElseThrow(EntityNotFoundException::new);
        log.info("{} {}", user.getFirstName(), user.getLastName());

        assertThat(optionalUser).isNotEmpty();
    }
}
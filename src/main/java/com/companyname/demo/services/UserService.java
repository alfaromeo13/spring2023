package com.companyname.demo.services;

import com.companyname.demo.dto.UserCreateDTO;
import com.companyname.demo.dto.UserTestDTO;
import com.companyname.demo.dto.UserUpdateDTO;
import com.companyname.demo.projections.UserFirstAndLastNameProjection;
import com.companyname.demo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class UserService {

    @Autowired
    @Qualifier("userDTO") //this finds alias from spring bean container
    private UserTestDTO user1;

    @Autowired
    @Qualifier("user2DTO")
    private UserTestDTO user2;

    @Autowired
    private UserRepository userRepository;

    public void printAllDetails() {
        log.info("User 1 details -> {}", user1);
        log.info("User 2 details -> {}", user2);
    }

    public List<UserTestDTO> getAll() {
        return List.of(
                new UserTestDTO(1L, "jovan", 22, "masline bb"),
                new UserTestDTO(2L, "Darko", 21, "preko morace"),
                new UserTestDTO(3L, "Marko", 20, "donja gorica")
        );
    }

    public Optional<UserTestDTO> getById(Long id) {
        return Stream.of(
                new UserTestDTO(1L, "jovan", 22, "masline bb"),
                new UserTestDTO(2L, "Darko", 21, "masline bb"),
                new UserTestDTO(3L, "Marko", 20, "murtovine bb")
        ).filter(u -> u.getId().equals(id)).findFirst();
    }

    public List<UserTestDTO> findByAddressStartingWith(String address) {
        return Stream.of(
                new UserTestDTO(1L, "jovan", 22, "preko morace bb"),
                new UserTestDTO(2L, "Darko", 21, "preko maslina bb"),
                new UserTestDTO(3L, "Marko", 20, "donja gorica bb")
        ).filter(u -> u.getAddress().startsWith(address)).collect(Collectors.toList());
    }

    public void create(UserCreateDTO userCreateDTO) {
        log.info("Created user -> {}", userCreateDTO);
    }

    public void update(Long id, UserUpdateDTO usr) {
        log.info("updated user -> {}", new UserTestDTO(id, usr.getFullName(), usr.getAge(), usr.getAddress()));
    }

    public Page<String> printWithPagination(Pageable pageable) {
        return userRepository.findUserNameInDepartmentIdWithPagination(1, pageable);
    }

    public Slice<String> printWithSlice(Pageable pageable) {
        return userRepository.findUserNameInDepartmentIdWithSlicing(1, pageable);
    }

    public List<UserFirstAndLastNameProjection> findProjection(Integer id) {
        return userRepository.findUserNameInDepartmentIdCustomProjection(id);
    }
}
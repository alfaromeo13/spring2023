package com.companyname.demo.services;

import com.companyname.demo.dto.UserCreateDTO;
import com.companyname.demo.dto.UserDTO;
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
    private UserDTO user1;

    @Autowired
    @Qualifier("user2DTO")
    private UserDTO user2;

    @Autowired
    private UserRepository userRepository;

    public void printAllDetails() {
        log.info("User 1 details -> {}", user1);
        log.info("User 2 details -> {}", user2);
    }

    public List<UserDTO> getAll() {
        return List.of(
                new UserDTO(1, "jovan", 22, "masline bb"),
                new UserDTO(2, "Darko", 21, "preko morace"),
                new UserDTO(3, "Marko", 20, "donja gorica")
        );
    }

    public Optional<UserDTO> getById(Integer id) {
        return Stream.of(
                new UserDTO(1, "jovan", 22, "masline bb"),
                new UserDTO(2, "Darko", 21, "masline bb"),
                new UserDTO(3, "Marko", 20, "masline bb")
        ).filter(u -> u.getId().equals(id)).findFirst();
    }

    public List<UserDTO> findByAddressStartingWith(String address) {
        return Stream.of(
                new UserDTO(1, "jovan", 22, "preko morace bb"),
                new UserDTO(2, "Darko", 21, "preko maslina bb"),
                new UserDTO(3, "Marko", 20, "donja gorica bb")
        ).filter(u -> u.getAddress().startsWith(address)).collect(Collectors.toList());
    }

    public void create(UserCreateDTO userCreateDTO) {
        log.info("Created user -> {}", userCreateDTO);
    }

    public void update(Integer id, UserUpdateDTO usr) {
        log.info("updated user -> {}", new UserDTO(id, usr.getFullName(), usr.getAge(), usr.getAddress()));
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
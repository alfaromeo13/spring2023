package com.companyname.demo.repositories;

import com.companyname.demo.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    //JPA Method Queries
    List<Department> findByName(String name);

    List<Department> findByNameStartingWith(String name);

    List<Department> findByNameAndDescription(String name, String description);

    List<Department> findByIsActiveTrue();

    //JPQL
    @Query(value = "select department from Department department " +
            "where department.name like concat('%', :name ,'%') ")
    List<Department> findByNameJPQL(@Param("name") String name);

    @Query(value = "select department from Department department " +
            "where department.name like concat(:namePart,'%') " +
            "and department.description like concat(:desc,'%') ")
    List<Department> findByNameAndDescJPQL(@Param("namePart") String name,
                                           @Param("desc") String description);

    //Pageable examples
    //It doesn't matter if relation is LAZY Pageable initializes queries for LAZY relations
    @Query(value = "select department from Department department " +
            "where department.name like concat(?1 , '%')")
    Page<Department> findByNameWithoutUsers(String departmentName, Pageable pageable);

    //HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory
    //this means pagination is done in RAM.This means hibernate will bring EVERYTHING from table in RAM
    //no matter how many rows table have and will be doing pagination inside the memory.And pagination is not
    //done by database but by Java itself!If we have a million rows in table our application would crash
    //Everything will work ,for small number of rows we wouldn't feel this performance problem but for many rows we would
    @Query(value = "select department from Department department " +
            " join fetch department.users users where department.name like concat(?1 , '%')")
    Page<Department> findByNameWithUsers(String departmentName, Pageable pageable);

    //how we solve this?
    //~ Only way to solve this is by creating two queries!
    //1.fetch department ids by department name with limit
    @Query(value = "select department.id from Department department where department.name = :name")
    Page<Integer> findIdsByName(@Param("name") String departmentName, Pageable pageable);

    //2.fetch departments with users where department.id in [collection from last query] (WITH NO LIMIT!)
    @Query(value = "select distinct department from Department department " +
            "join fetch department.users where department.id in (:ids)")
    List<Department> findByIdsWithUsers(@Param("ids") List<Integer> departmentIds);
    //NOTE:: we can't return Page<Department> to the end user.We have to manually create the Page
}
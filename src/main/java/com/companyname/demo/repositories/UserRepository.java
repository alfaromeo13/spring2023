package com.companyname.demo.repositories;

import com.companyname.demo.entities.User;
import com.companyname.demo.projections.UserFirstAndLastNameProjection;
import com.companyname.demo.records.UserFirstAndLastNameRecord;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //for these logical questions JPA methods are very nice and quick
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    //left join fetch because we want user even if he doesn't have any roles
    @Query(value = """
            select user from User user left join fetch user.roles where user.id = ?1
            """)
    User getUserWithRoles(Long id);

    @Query(value = """
            select user from User user left join fetch user.roles where user.username = ?1
            """)
    Optional<User> findByUsernameWithRoles(String username);

    // select * from users join
    // departments on user.fk_department = department.id
    // where user.username = ? and department.name = ?
    List<User> findByFirstNameAndDepartmentName(String username, String departmentName);

    // select * from users join
    // departments on user.fk_department = department.id
    // where department.name = ? and department.description LIKE '%?'
    List<User> findByDepartmentNameAndDepartmentDescriptionEndingWith(String name, String desc);


    //if we put join fetch value of department won't be null
    //also department field has  @JoinColumn(name = "fk_department")
    //which tells hibernate on which column join is going to be made
    @Query(value = "select user from User user join user.department department" +
            " where user.firstName = :name and department.name = :department")
    List<User> findByNameAndDepartmentNAmeJPQL(@Param("name") String firstName, @Param("department") String dept);

    //select user.first_name from users join departments on
    //user.department.id=department.id where department.id= ?
    @Query(value = "select user.firstName from User user" +
            " join user.department department where department.id = ?1 ")
    List<String> findUserNameInDepartmentId(Integer id);
    //?1 means first method argument

    //select user.first_name from users user where user.department.id= ?
    @Query(value = "select user.firstName from User user where user.department.id = ?1")
    List<String> findUserNameInDepartmentIdVol2(Integer id);

    // difference between last two methods is consistency!They do the same
    // meaning they will produce the same output.First one assures us that
    // apartment which is joined on really exists (since the join is done
    // by foreign key).If in user table we have some not consistent data
    // (let's say outdated) example we delete department 1 but user still has
    // fk_department=1.First query would say 'error' while second one would return
    // dep. 1 .So we use the second one only if we are 100% sure that department which
    // is passed exists second one is better for performance
    // (since join can be exhausting operation)

    //text block example
    @Query(value = """
            select user.firstName from User user
            join user.department department where department.id = ?1
            """)
    List<String> blockExample(Integer id);

    //1.Mapping using Records (since Java 15) .This is alternative to mapping with DTO classes
    @Query(value =
            "select new com.companyname.demo.records.UserFirstAndLastNameRecord(user.firstName,user.lastName)" +
                    " from User user join user.department department where department.id= ?1")
    List<UserFirstAndLastNameRecord> getByDepartmentIdWithRecord(Integer id);

    //2.Mapping using Tuple (worst way)
    //if we need to return projection of 2 values we use tuple
    //We set alias to our custom names for better access, and also because hibernate changes colum
    //names to something like col_01 ... which loses column meaning,also makes mapping difficult for us
    @Query(value = "select user.firstName as firstName,user.lastName as lastName " +
            "from User user where user.department.id = ?1")
    List<Tuple> findUserNameInDepartmentIdTuple(Integer id);

    //3.Mapping using ProjectionInterface mapping (the best way since mapping with DTO constructor
    // can be very big depending on number of constructor arguments )
    @Query(value = "select user.firstName as firstName,user.lastName as lastName " +
            "from User user where user.department.id = ?1")
    List<UserFirstAndLastNameProjection> findUserNameInDepartmentIdCustomProjection(Integer id);

    //pagination technique example
    @Query(value = "select user.firstName from User user" +
            " join user.department department where department.id = ?1 ")
    Page<String> findUserNameInDepartmentIdWithPagination(Integer id, Pageable pageable);

    //slicing technique (better than pagination for performances)
    @Query(value = "select user.firstName from User user" +
            " join user.department department where department.id = ?1 ")
    Slice<String> findUserNameInDepartmentIdWithSlicing(Integer id, Pageable pageable);
}
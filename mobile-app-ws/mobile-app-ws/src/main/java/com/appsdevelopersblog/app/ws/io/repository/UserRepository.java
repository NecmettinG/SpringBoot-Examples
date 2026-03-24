package com.appsdevelopersblog.app.ws.io.repository;

import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//We deleted CrudRepository<UserEntity, Long> for pagination in get request that returns list of users.
//Teacher put PagingAndSortingRepository<UserEntity, Long> but .save() methods doesn't work so We have to use JpaRepository<UserEntity, Long>-
//-instead.
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> { //We can also use JpaRepository<UserEntity, Long>

    //These are called query methods. I already know how these work. "-Email" and "-UserId" comes from email and userId variables from-
    // -UserEntity.
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
    UserEntity findUserByEmailVerificationToken(String emailVerificationToken);

    /*This query structure is called native query. We are writing complete sql queries into @Query annotation. In query methods, method-
    name does matter for converting the name into sql query by jpa. But in native queries, method name can be anything.
    This method will return all users with confirmed email addresses from database.
    countQuery and count() need to be used if this is a pageable request.*/
    @Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = true",
            countQuery = "select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = true",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);

    //?1 means place a value of method argument one. Our first parameter is firstName btw. We will replace ?1 with firstName's value.
    @Query(value = "select * from Users u where u.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);

    //This method is created only for demonstrating multiple method arguments usage for native query.
    @Query(value = "select * from Users u where u.first_name = ?1 and u.last_name = ?2", nativeQuery = true)
    List<UserEntity> findUserByFirstNameAndLastName(String firstName, String lastName);

    //Instead of using ?1, this time we use :lastName which represents the certain method argument with annotation @Param("lastName").
    @Query(value = "select * from Users u where u.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);
}

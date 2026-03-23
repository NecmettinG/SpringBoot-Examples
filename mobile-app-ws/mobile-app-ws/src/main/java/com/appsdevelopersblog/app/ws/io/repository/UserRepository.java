package com.appsdevelopersblog.app.ws.io.repository;

import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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
}

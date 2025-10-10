package com.appsdevelopersblog.app.ws.io.repository;

import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//We deleted CrudRepository<UserEntity, Long> for pagination in get request that returns list of users.
//Teacher put PagingAndSortingRepository<UserEntity, Long> but .save() methods doesn't work so We have to use JpaRepository<UserEntity, Long>-
//-instead.
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> { //We can also use JpaRepository<UserEntity, Long>

    //These are called query methods. I already know how these work.
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}

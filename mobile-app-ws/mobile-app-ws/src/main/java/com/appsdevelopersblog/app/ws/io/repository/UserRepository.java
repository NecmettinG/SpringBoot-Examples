package com.appsdevelopersblog.app.ws.io.repository;

import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> { //We can also use JpaRepository<UserEntity, Long>

    //These are called query methods. I already know how these work.
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}

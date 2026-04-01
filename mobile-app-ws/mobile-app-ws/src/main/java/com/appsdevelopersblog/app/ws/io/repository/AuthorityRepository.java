package com.appsdevelopersblog.app.ws.io.repository;

import com.appsdevelopersblog.app.ws.io.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity,Long> {

    AuthorityEntity findByName(String name);
}

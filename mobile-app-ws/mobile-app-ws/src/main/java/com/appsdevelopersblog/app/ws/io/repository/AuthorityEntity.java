package com.appsdevelopersblog.app.ws.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityEntity extends JpaRepository<AuthorityEntity ,Long> {

    AuthorityEntity findByName(String name);
}

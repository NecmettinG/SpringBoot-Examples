package com.appsdevelopersblog.app.ws.io.repository;

import com.appsdevelopersblog.app.ws.io.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
}

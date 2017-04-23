package com.highgag.core.repository;

import com.highgag.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u.id) > 0 FROM User u WHERE u.account = :account")
    boolean existsByAccount(@Param("account") String account);

    @Query("SELECT COUNT(u.id) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    User findByAccount(String account);
}

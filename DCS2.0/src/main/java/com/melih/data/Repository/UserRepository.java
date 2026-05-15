package com.melih.data.Repository;

import com.melih.data.Entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository
        extends JpaRepository<User, Long> {

    // Derived Query
    List<User> findByUsername(String username);

    // Custom Query
    @Query("""
           SELECT u
           FROM User u
           WHERE u.username = :username
           """)
    User findCustom(@Param("username") String username);

    // Fetch Join
    @Query("""
           SELECT u
           FROM User u
           JOIN FETCH u.orders
           """)
    List<User> findAllWithOrders();
}

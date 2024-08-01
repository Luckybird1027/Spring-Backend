package com.luckybird.backendapp.reposity;

import com.luckybird.backendapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author 新云鸟
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByusername(String username);

    User findByid(Long id);
}

package com.luckybird.springbackend.reposity;

import com.luckybird.springbackend.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author 新云鸟
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByUsernameContaining(String username, Pageable pageable);
}

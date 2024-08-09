package com.luckybird.springbackend.reposity;

import com.luckybird.springbackend.po.UserPO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author 新云鸟
 */
public interface UserRepository extends JpaRepository<UserPO, Long> {
    Optional<UserPO> findByUsername(String username);

    List<UserPO> findByUsernameContaining(String username, Pageable pageable);

    List<UserPO> findByUsernameContaining(String username);

    Integer countByUsernameContaining(String username);

    List<UserPO> findByIdIn(List<Long> ids);
}

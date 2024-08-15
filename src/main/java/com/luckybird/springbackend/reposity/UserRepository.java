package com.luckybird.springbackend.reposity;

import com.luckybird.springbackend.po.UserPO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 用户JPA仓储接口
 *
 * @author 新云鸟
 */
public interface UserRepository extends JpaRepository<UserPO, Long> {
    Optional<UserPO> findByAccount(String account);

    List<UserPO> findByUsernameContaining(String username, Pageable pageable);

    List<UserPO> findByUsernameContaining(String username);

    Integer countByUsernameContaining(String username);

    List<UserPO> findByIdIn(Set<Long> ids);
}

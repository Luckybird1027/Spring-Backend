package com.luckybird.springbackend.reposity;

import com.luckybird.springbackend.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * 用户JPA仓储接口
 *
 * @author 新云鸟
 */
public interface UserRepository extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO> {

    Optional<UserPO> findByAccount(String account);
}

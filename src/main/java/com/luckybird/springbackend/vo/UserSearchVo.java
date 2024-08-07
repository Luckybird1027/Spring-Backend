package com.luckybird.springbackend.vo;

import com.luckybird.springbackend.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 新云鸟
 */
@Getter
@Setter
public class UserSearchVo {
    private Long total;
    private List<User> users;

    public UserSearchVo(List<User> users, Long count) {
        this.users = users;
        this.total = count;
    }

    public UserSearchVo(List<User> users) {
        this.users = users;
        this.total = null;
    }
}

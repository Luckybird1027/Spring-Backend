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
    private Integer total;
    private List<User> users;

    public UserSearchVo(List<User> users, Integer count) {
        this.users = users;
        this.total = count;
        // TODO: 当searchCount为false时，total仍然出现在回应json中，需修改
    }

    public UserSearchVo(List<User> users) {
        this.users = users;
    }
}

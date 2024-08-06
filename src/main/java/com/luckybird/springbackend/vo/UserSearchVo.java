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

    public UserSearchVo() {
    }

    public UserSearchVo(List<User> users, boolean searchCount) {
        this.users = users;
        // TODO: 当searchCount为false时，total仍然出现在回应json中，需修改
        if (searchCount) {
            this.total = users.size();
        }
        else {
            this.total = null;
        }
    }
}

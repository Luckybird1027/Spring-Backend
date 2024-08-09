package com.luckybird.springbackend.vo;

import com.luckybird.springbackend.po.UserPO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 新云鸟
 */
@Getter
@Setter
public class UserSearchVO {
    private Long total;
    private List<UserPO> poList;

    public UserSearchVO(List<UserPO> poList, Long count) {
        this.poList = poList;
        this.total = count;
    }

    public UserSearchVO(List<UserPO> poList) {
        this.poList = poList;
        this.total = null;
    }
}

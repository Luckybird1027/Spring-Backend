package com.luckybird.springbackend.api.req;

import lombok.Data;

/**
 * 用户查询请求
 *
 * @author 新云鸟
 */
@Data
public class UserQueryReq {

    /**
     * 搜索关键字
     */
    private String keyword;
}

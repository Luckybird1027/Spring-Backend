package com.luckybird.springbackend.api.req;

import lombok.Data;

/**
 * 部门查询请求
 *
 * @author 新云鸟
 */
@Data
public class DeptQueryReq {

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 父部门ID
     */
    private Long parentId;

}

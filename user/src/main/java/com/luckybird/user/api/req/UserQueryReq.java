package com.luckybird.user.api.req;

import com.luckybird.repository.constant.UserStatusEnum;
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

    /**
     * 状态
     *
     * @see UserStatusEnum
     */
    private Integer status;

    /**
     * 组织ID
     */
    private Long organizationId;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 职位
     */
    private String occupation;
}

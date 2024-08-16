package com.luckybird.springbackend.api.req;

import com.luckybird.springbackend.converter.OccupationConverter;
import jakarta.persistence.Convert;
import lombok.Data;

import java.util.List;

/**
 * 用户更新请求
 *
 * @author 新云鸟
 */
@Data
public class UserUpdateReq {

    /**
     * ID
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String username;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
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
    @Convert(converter = OccupationConverter.class)
    private List<String> occupation;

    /**
     * 备注
     */
    private String remark;
}

package com.luckybird.repository.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.luckybird.repository.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 用户实体
 *
 * @author 新云鸟
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "user", autoResultMap = true)
public class UserPO extends BasePO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> occupation;

    // TODO：权限系统待定
//    /**
//     * 权限角色
//     */
//    @TableField(typeHandler = JacksonTypeHandler.class)
//    private List<String> permissionRole;

    /**
     * 备注
     */
    private String remark;

}
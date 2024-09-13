package com.luckybird.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.luckybird.repository.constant.UserStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 用户实体
 *
 * @author 新云鸟
 */
@Data
@TableName(value = "user", autoResultMap = true)
public class UserPO {

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人ID
     */
    private Long updaterId;

    /**
     * 逻辑删除
     */
    @TableLogic(value = "0", delval = "id")
    private Long deleted;
}
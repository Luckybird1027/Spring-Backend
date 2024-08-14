package com.luckybird.springbackend.po;

import com.luckybird.springbackend.converter.OccupationConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.util.List;


/**
 * 用户实体
 *
 * @author 新云鸟
 */
@Data
@Entity
@DynamicInsert
@Table(name = "user")
public class UserPO {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Byte status;

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

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 更新人ID
     */
    private Long updaterId;

    /**
     * 逻辑删除
     */
    private Long deleted;
}

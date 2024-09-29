package com.luckybird.repository.dept;


import com.baomidou.mybatisplus.annotation.TableName;
import com.luckybird.repository.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门实体
 *
 * @author 新云鸟
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "dept", autoResultMap = true)
public class DeptPO extends BasePO {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 部门路径
     */
    private String path;

    /**
     * 备注
     */
    private String remark;

}

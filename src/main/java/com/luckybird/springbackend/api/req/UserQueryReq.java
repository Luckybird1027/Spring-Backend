package com.luckybird.springbackend.api.req;

import com.luckybird.springbackend.converter.OccupationConverter;
import jakarta.persistence.Convert;
import lombok.Data;

import java.util.List;

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
}

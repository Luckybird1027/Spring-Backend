package com.luckybird.springbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luckybird.springbackend.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 新云鸟
 */

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
    List<UserPO> selectByConditions(@Param("keyword") String keyword,
                                    @Param("status") Integer status,
                                    @Param("organizationId") Long organizationId,
                                    @Param("departmentId") Long departmentId,
                                    @Param("occupation") String occupation);

    List<UserPO> selectByConditionsWithPage(@Param("keyword") String keyword,
                                            @Param("status") Integer status,
                                            @Param("organizationId") Long organizationId,
                                            @Param("departmentId") Long departmentId,
                                            @Param("occupation") String occupation,
                                            @Param("page") Long page,
                                            @Param("size") Long size);

    Long countByConditions(@Param("keyword") String keyword,
                           @Param("status") Integer status,
                           @Param("organizationId") Long organizationId,
                           @Param("departmentId") Long departmentId,
                           @Param("occupation") String occupation);
}

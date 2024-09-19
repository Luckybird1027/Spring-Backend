package com.luckybird.repository.operateLog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.luckybird.common.base.Difference;
import com.luckybird.common.base.KeyValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 操作日志实体
 *
 * @author 新云鸟
 */
@Data
@TableName(value = "operate_log", autoResultMap = true)
public class OperateLogPO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 操作模块
     */
    private String operateModule;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 操作功能
     */
    private String operateFeature;

    /**
     * 数据摘要
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<KeyValue> dataBrief;

    /**
     * 数据差异
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Difference> dataDifference;

    /**
     * 操作者ID
     */
    private Long operatorId;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 客户端UA
     */
    private String clientUa;

}
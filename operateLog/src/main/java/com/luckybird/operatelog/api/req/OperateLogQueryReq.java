package com.luckybird.operatelog.api.req;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志查询请求
 *
 * @author 新云鸟
 */
@Data
public class OperateLogQueryReq {

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
     * 操作者ID
     */
    private Long operatorId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}

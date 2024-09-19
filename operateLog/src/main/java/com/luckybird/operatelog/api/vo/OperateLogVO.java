package com.luckybird.operatelog.api.vo;

import com.luckybird.common.base.Difference;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志VO
 *
 * @author 新云鸟
 */
@Data
public class OperateLogVO {

    /**
     * ID
     */
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
    private Object dataBrief;

    /**
     * 数据差异
     */
    private List<Difference> dataDifference;

    /**
     * 操作者ID
     */
    private Long operatorId;

    /**
     * 操作者昵称/操作者账号
     */
    private String operatorName;

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

package com.luckybird.operatelog.api.req;

import lombok.Data;

/**
 * 操作日志查询请求
 *
 * @author 新云鸟
 */
@Data
public class OperateLogQueryReq {

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 操作者ID
     */
    private Long operatorId;
}

package com.luckybird.repository.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础PO类
 *
 * @author 新云鸟
 */
@Data
public class BasePO {

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
    @TableLogic(value = "-1", delval = "id")
    private Long deleted;
}

package com.luckybird.springbackend.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 新云鸟
 */
@Getter
@Setter
public class PageResult<T> {

    /**
     * 总记录数
     */
    private Long total;
    /**
     * 分页数据
     */
    private List<T> items;

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(List<T> items) {
        this.items = items;
    }
}

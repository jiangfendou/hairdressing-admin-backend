package com.jiangfendou.share.entiy;

import lombok.Data;

/**
 * BaseEntity.
 * @author jiangmh
 */
public class BaseEntity {

    private Integer pageSize;

    private Integer pageNum;

    public Integer getPageSize() {
        return (this.pageSize == null || this.pageSize == 0) ? 10 : this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return (this.pageNum == null || this.pageNum == 0) ? 1 : this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}

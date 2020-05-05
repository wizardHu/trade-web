package com.wizard.model.from;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页查询基础
 */
public class CommonPaginationQueryBase {
    /**页码*/
    @NotNull(message = "页码不能为空")
    @Min(value = 1,message = "页码不能小于1")
    private Integer page;
    /**每页大小*/
    @NotNull(message = "每页大小不能为空")
    @Max(value = 100000,message = "每页大小不能超过100000")
    private Integer pageSize;

    private Integer startIndex;

    public Integer generateStartIndex(){
        if(pageSize == null){
            return null;
        }
        if(page == null){
            page = 1;
        }
        startIndex = (page - 1) * pageSize;
        return startIndex;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}

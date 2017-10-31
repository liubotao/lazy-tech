package com.whh.common.utils.util;

/**
 * Created by huahui.wu on 2017/10/31.
 */
public class BaseQuery {
    private Integer page;
    private Integer size;
    private String sort;

    public BaseQuery() {
    }

    public int getPage() {
        return this.page == null?1:this.page.intValue();
    }

    public void setPage(int page) {
        this.page = Integer.valueOf(page);
    }

    public int getSize() {
        return this.size == null?10:this.size.intValue();
    }

    public void setSize(int size) {
        this.size = Integer.valueOf(size);
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}

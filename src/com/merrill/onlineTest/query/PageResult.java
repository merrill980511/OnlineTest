package com.merrill.onlineTest.query;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResult {
    private List listData;
    private Integer totalCount;

    private Integer currentPage = 1;
    private Integer pageSize = 5;

    private Integer beginPage = 1;
    private Integer prevPage;
    private Integer nextPage;
    private Integer totalPage;

    private Integer[] integers = {5, 10, 15, 20};
    private List<Integer> pageItems = new ArrayList<>();

    public PageResult(Integer currentPage, Integer pageSize,
                      Integer totalCount){
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;

        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        this.prevPage = currentPage - 1 >= 1 ? currentPage - 1 : 1;
        this.nextPage = currentPage + 1 <= totalPage ? currentPage + 1 : totalPage;

        if (currentPage > totalPage && currentPage != 1){
            this.currentPage = totalPage;
        }

        for(int i = 0; i < integers.length; i++){
            pageItems.add(integers[i]);
        }
    }

}

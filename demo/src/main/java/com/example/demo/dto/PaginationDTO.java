package com.example.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO{
    private List<QuestionDTO> questions;
    private List<Integer> pages = new ArrayList<>();
    private Integer page;
    private Integer size;
    private Integer totalPage;
    private boolean showFirstPage;
    private boolean showLastPage;
    private boolean showPreviousPage;
    private boolean showNextPage;
    private final Integer displayPageBtnCount = 7;
    public void setPagination(Integer questionCount, Integer page, Integer size) {
        //计算页面总页数
        totalPage = questionCount % size == 0 ? questionCount/size : questionCount/size + 1;
        //越界判断
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        this.page = page;
        this.size = size;

        //判断是否显示上一页和下一页
        if(page > 1){
            showPreviousPage = true;
        }
        if(page < totalPage){
            showNextPage = true;
        }
        int half = displayPageBtnCount/2;
        //判断是否显示首页和尾页
        if(page > half + 1){
            showFirstPage = true;
        }
        if(page < totalPage - half){
            showLastPage = true;
        }
        //把要显示的页码放进对象
        pages.add(page);
        for(int i = 1;i <= half;i++){
            if(page - i > 0){
                pages.add(0,page - i);
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }
    }
}

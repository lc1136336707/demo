package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
}

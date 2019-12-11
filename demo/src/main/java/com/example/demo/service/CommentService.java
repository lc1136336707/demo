package com.example.demo.service;

import com.example.demo.Exception.CustomizeErrorCode;
import com.example.demo.Exception.CustomizeException;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.enums.CommentType;
import com.example.demo.mapper.CommentEXMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.QuestionEXMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionEXMapper questionEXMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentEXMapper commentEXMapper;

    public ResultDTO insert(CommentDTO commentDTO) {
        if(commentDTO.getParentId() == null || commentDTO.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        if(commentDTO.getType() == null || !CommentType.isExist(commentDTO.getType())){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_TYPE_NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(comment.getGmtCreate());
        comment.setCommentator(commentDTO.getCommentator());
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        if(commentDTO.getType() == CommentType.COMMENT.getType()){
            Comment parentComment = commentMapper.selectByPrimaryKey(commentDTO.getParentId());
            if(parentComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_MOT_FOUND);
            }
            commentMapper.insert(comment);
            parentComment.setCommentCount(1);
            commentEXMapper.incCommentCount(parentComment);
        }
        if(commentDTO.getType() == CommentType.QUESTION.getType()){
            Question question = questionMapper.selectByPrimaryKey(commentDTO.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionEXMapper.incCommentCount(question);
        }
        return ResultDTO.errorOf(CustomizeErrorCode.SUCCESS);
    }
}

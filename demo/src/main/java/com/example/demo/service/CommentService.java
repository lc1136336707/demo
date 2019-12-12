package com.example.demo.service;

import com.example.demo.Exception.CustomizeErrorCode;
import com.example.demo.Exception.CustomizeException;
import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.enums.CommentType;
import com.example.demo.mapper.*;
import com.example.demo.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private UserMapper userMapper;
    //查找评论
    public List findByParentId(Long id) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                                    .andParentIdEqualTo(id)
                                    .andTypeEqualTo(CommentType.QUESTION.getType());
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if(commentList == null || commentList.size() == 0){
            return new ArrayList();
        }
        Set<Long> commentCreators = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentCreators);

        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        for (Comment comment : commentList) {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(commentDTO.getCommentator()));
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public ResultDTO insert(CommentCreateDTO commentCreateDTO) {
        if(commentCreateDTO.getParentId() == null || commentCreateDTO.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        if(commentCreateDTO.getType() == null || !CommentType.isExist(commentCreateDTO.getType())){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_TYPE_NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(comment.getGmtCreate());
        comment.setCommentator(commentCreateDTO.getCommentator());
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        if(commentCreateDTO.getType() == CommentType.COMMENT.getType()){
            Comment parentComment = commentMapper.selectByPrimaryKey(commentCreateDTO.getParentId());
            if(parentComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_MOT_FOUND);
            }
            commentMapper.insert(comment);
            parentComment.setCommentCount(1);
            commentEXMapper.incCommentCount(parentComment);
        }
        if(commentCreateDTO.getType() == CommentType.QUESTION.getType()){
            Question question = questionMapper.selectByPrimaryKey(commentCreateDTO.getParentId());
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

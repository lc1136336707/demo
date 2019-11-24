package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create (Question question);
    @Select("select * from question limit #{offset},#{size}")
    List<Question> selQuestions(@Param("offset") Integer offset, @Param("size") Integer size);
    @Select("select count(1) from question")
    Integer getCount();
    @Select("select count(1) from question where creator = #{userId} ")
    Integer getCountById(Integer userId);
    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> selQuestionsById(Integer userId, Integer offset, Integer size);
    @Select("select * from question where id = #{id}")
    Question findById(Integer id);
    @Update("update question set title = #{title},description = #{description},tag = #{tag} where id = #{id}")
    void update(@Param("id") Integer id, @Param("title") String title, @Param("description") String description, @Param("tag") String tag);
}

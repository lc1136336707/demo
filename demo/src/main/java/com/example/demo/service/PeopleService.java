package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {
    @Autowired
    private UserMapper userMapper;
    public User getUserById(Long userId) {

        return userMapper.selectByPrimaryKey(userId);
    }
}

package com.example.demo.service;

import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User createOrUpdate(GithubUser githubUser) {
        User user = userMapper.findByAccountId(String.valueOf(githubUser.getId()));
        if(user != null){
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.update(user);
        }else{
            user = new User();
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(githubUser.getId()));
            userMapper.insert(user);
        }
            return user;
    }
}

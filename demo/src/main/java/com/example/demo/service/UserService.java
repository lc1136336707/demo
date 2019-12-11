package com.example.demo.service;

import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User createOrUpdate(GithubUser githubUser) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(String.valueOf(githubUser.getId()));
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() != 0){
            User updateUser = new User();
            updateUser.setName(githubUser.getName());
            updateUser.setAvatarUrl(githubUser.getAvatar_url());
            updateUser.setGmtModified(System.currentTimeMillis());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(users.get(0).getId());
            userMapper.updateByExampleSelective(updateUser,userExample1);
            return users.get(0);
        }else{
            User user = new User();
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(githubUser.getId()));
            userMapper.insert(user);
            return user;
        }
    }
}

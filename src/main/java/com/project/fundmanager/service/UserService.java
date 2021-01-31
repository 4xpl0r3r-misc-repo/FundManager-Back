package com.project.fundmanager.service;

import com.project.fundmanager.entity.User;
import com.project.fundmanager.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserMapper userMapper;

    public User getUserById(long id) {
        return userMapper.getById(id);
    }

    public User getUserByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    public List<User> getUserList(int offset,int maxResults){
        return userMapper.getAll(offset,maxResults);
    }

    public User login(String email, String password) {
        logger.info("try login by {}...", email);
        User user = getUserByEmail(email);
        if (user.checkPassword(password)) {
            return user;
        }
        throw new RuntimeException("login failed.");
    }

    public User register(String email, String password, String name) {
        logger.info("try register by {}...", email);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setRegisteredAt(new Date(System.currentTimeMillis()));
        userMapper.insert(user);
        return user;
    }

    public void updateUserName(Long id, String name) {
        User user = getUserById(id);
        user.setName(name);
        userMapper.update(user);
    }

    public void updateUserPassword(Long id, String password) {
        User user = getUserById(id);
        user.setPassword(password);
        userMapper.update(user);
    }

    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }
}

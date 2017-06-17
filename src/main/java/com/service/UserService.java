package com.service;

import com.dao.UserDao;
import com.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by geser on 16.06.17.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public UserService (){

    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public UserEntity getUser(int id){
        return userDao.getUser(id);
    }

    @Transactional
    public boolean isExist(String login, String password){
        return userDao.isExist(login, password);
    }

    @Transactional
    public UserEntity validateUser(String login, String password){
        return userDao.validateUser(login, password);
    }

    @Transactional
    public void createUser(UserEntity newUser){
        userDao.createUser(newUser);
    }

    @Transactional
    public void updateUser(UserEntity updatedUser){
        userDao.createUser(updatedUser);
    }

    @Transactional
    public void deleteUser(UserEntity user){
        userDao.createUser(user);
    }

    public ArrayList<UserEntity> getAllUsers(){
        return  userDao.getAllUSers();
    }

}

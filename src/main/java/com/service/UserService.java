package com.service;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDAO;
import com.entity.UserEntity;

@Service
public class UserService{

	@Autowired
	UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public UserService() {

	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@org.springframework.transaction.annotation.Transactional
	public void createUser(UserEntity userEntity) {
		userDAO.create(userEntity);
	}

	@org.springframework.transaction.annotation.Transactional
	public void deleteUser(UserEntity userEntity) {
		userDAO.delete(userEntity);
	}

	@org.springframework.transaction.annotation.Transactional
	public void updateUser(UserEntity userEntity) {
		userDAO.update(userEntity);
	}

	@org.springframework.transaction.annotation.Transactional
	public UserEntity getUser(Integer id) {
		return userDAO.getUser(id);
	}

	@org.springframework.transaction.annotation.Transactional
	public List<UserEntity> getAllUsers() {
		return userDAO.getAllUSers();
	}

	@org.springframework.transaction.annotation.Transactional
	public UserEntity validateUser(String username, String password) {
		return userDAO.validateUser(username, password);
	}

}

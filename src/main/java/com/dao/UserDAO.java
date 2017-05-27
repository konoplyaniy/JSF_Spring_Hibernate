package com.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.beans.Mail;
import com.entity.UserEntity;
import com.utils.SupportUtils;

@Repository("userDAO")
public class UserDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<UserEntity> getAllUSers() {
			@SuppressWarnings("unchecked")
			List<UserEntity> users = sessionFactory.getCurrentSession().createCriteria(UserEntity.class).list();
			return users;
	}

	public UserEntity getUser(Integer user_id) {
			Query query = sessionFactory.getCurrentSession().createQuery("from UserEntity where user_id=:user_id");
			query.setParameter("user_id", user_id);
			UserEntity user = (UserEntity) query.uniqueResult();
			return user;
	}

	public void create(UserEntity userEntity) {
		sessionFactory.getCurrentSession().persist(userEntity);
	}

	public UserEntity validateUser(String username, String password) {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("from UserEntity where username=:username and password=:password");
			query.setParameter("username", username);
			query.setParameter("password", SupportUtils.MD5(password));
			UserEntity user = (UserEntity) query.uniqueResult();
			return user;
	}

	public void update(UserEntity userEntity) {
		sessionFactory.getCurrentSession().update(userEntity);
	}

	public void delete(UserEntity usEntity){
	sessionFactory.getCurrentSession().delete(usEntity);
	}

}

package com.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.entity.UserEntity;

import java.util.ArrayList;

@Repository("userDao")
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserEntity validateUser(String login, String password) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from UserEntity where login=:login and password=:password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        UserEntity user = (UserEntity) query.uniqueResult();
        return user;
    }

    public UserEntity getUser(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from UserEntity where id=:id");
        query.setParameter("id", id);
        return (UserEntity) query.uniqueResult();
    }

    public boolean isExist(String login, String password) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from UserEntity where login=:login and password=:password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        if (query.uniqueResult() != null) {
            return true;
        }
        return false;
    }

    public void createUser(UserEntity newUser) {
        sessionFactory.getCurrentSession().saveOrUpdate(newUser);
        System.out.println("user created");
    }

    public void updateUser(UserEntity updatedUser) {
        sessionFactory.getCurrentSession().persist(updatedUser);
        System.out.println("user updated");
    }

    public void deleteUser(UserEntity updatedUser) {
        sessionFactory.getCurrentSession().persist(updatedUser);
        System.out.println("user deleted");
    }

    public ArrayList<UserEntity> getAllUsers() {
        @SuppressWarnings("unchecked")
        ArrayList<UserEntity> users = (ArrayList<UserEntity>) sessionFactory.getCurrentSession().createCriteria(UserEntity.class).list();
        return users;
    }


}

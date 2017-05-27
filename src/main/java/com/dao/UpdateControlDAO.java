package com.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.SolvedTicketEntity;
import com.entity.UpdateControlEntity;

@Repository("updateControlDAO")
public class UpdateControlDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<UpdateControlEntity> getSolvedTickets(Integer user_id, Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from UpdateControlEntity where user_id=:user_id and month(date)=:month and year(date)=:year");
		query.setParameter("user_id", user_id);
		query.setParameter("month", date.getMonth() + 1);
		query.setParameter("year", calendar.get(Calendar.YEAR));
		List<UpdateControlEntity> updateList = (List<UpdateControlEntity>) query.list();
		return updateList;
	}

	public List<UpdateControlEntity> getAllUsersSolvedTickets(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from UpdateControlEntity where month(date)=:month and year(date)=:year");
		query.setParameter("month", date.getMonth() + 1);
		query.setParameter("year", calendar.get(Calendar.YEAR));
		List<UpdateControlEntity> updateList = (List<UpdateControlEntity>) query.list();
		return updateList;
	}
	
	public List<UpdateControlEntity> getAllUsersSolvedTickets(){
		List<UpdateControlEntity>updateList = sessionFactory.getCurrentSession().createCriteria(UpdateControlEntity.class).list();
		return updateList;
	}
	
	public void create(UpdateControlEntity updateControlEntity){
		sessionFactory.getCurrentSession().persist(updateControlEntity);
	}
	
	public void delete(UpdateControlEntity updateControlEntity){
		sessionFactory.getCurrentSession().delete(updateControlEntity);
	}
	
	public void update(UpdateControlEntity updateControlEntity){
		sessionFactory.getCurrentSession().update(updateControlEntity);
	}
}

package com.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.entity.CreateTicketEntity;
import com.entity.UserEntity;

@Repository("createTicketDAO")
public class CreateTicketDAO{

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<CreateTicketEntity> getCreateTickets(Integer user_id, Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from CreateTicketEntity where user_id=:user_id and month(date)=:month and year(date)=:year");
		query.setParameter("user_id", user_id);
		query.setParameter("month", date.getMonth() + 1);
		query.setParameter("year", calendar.get(Calendar.YEAR));
		List<CreateTicketEntity> createList = (List<CreateTicketEntity>) query.list();
		return createList;
	}

	public List<CreateTicketEntity> getAllUsersCreateTickets(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from CreateTicketEntity where month(date)=:month and year(date)=:year");
		query.setParameter("month", date.getMonth() + 1);
		query.setParameter("year", calendar.get(Calendar.YEAR));
		List<CreateTicketEntity>createList = (List<CreateTicketEntity>) query.list();
		return createList;
	}
	
	public List<CreateTicketEntity> getAllUsersCreateTickets(){
		List<CreateTicketEntity>createList = sessionFactory.getCurrentSession().createCriteria(CreateTicketEntity.class).list();
		return createList;
	}

	public void createTicket(CreateTicketEntity createTicketEntity) {
		sessionFactory.getCurrentSession().persist(createTicketEntity);
	}

	public void delete(CreateTicketEntity createTicketEntity){
		sessionFactory.getCurrentSession().delete(createTicketEntity);
	}
	
	public void update(CreateTicketEntity createTicketEntity){
		sessionFactory.getCurrentSession().update(createTicketEntity);
	}
}

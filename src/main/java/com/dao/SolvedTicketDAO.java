package com.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.CreateTicketEntity;
import com.entity.OvertimeEntity;
import com.entity.SolvedTicketEntity;

@Repository("solvedTicketDAO")
public class SolvedTicketDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public List<SolvedTicketEntity> getSolvedTickets(Integer user_id, Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from SolvedTicketEntity where user_id=:user_id and month(date)=:month and year(date)=:year");
		query.setParameter("user_id", user_id);
		query.setParameter("month", date.getMonth() + 1);
		query.setParameter("year", calendar.get(Calendar.YEAR));
		List<SolvedTicketEntity> solvedList = (List<SolvedTicketEntity>) query.list();
		return solvedList;
	}

	public List<SolvedTicketEntity> getAllUsersSolvedTickets(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from SolvedTicketEntity where month(date)=:month and year(date)=:year");
		query.setParameter("month", date.getMonth() + 1);
		query.setParameter("year", calendar.get(Calendar.YEAR));
		List<SolvedTicketEntity> solvedList = (List<SolvedTicketEntity>) query.list();
		return solvedList;
	}
	
	public List<SolvedTicketEntity> getAllUsersSolvedTickets(){
		List<SolvedTicketEntity>solvedList = sessionFactory.getCurrentSession().createCriteria(SolvedTicketEntity.class).list();
		return solvedList;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void create(SolvedTicketEntity solvedTicketEntity){
		sessionFactory.getCurrentSession().persist(solvedTicketEntity);
	}
	
	public void delete(SolvedTicketEntity solvedTicketEntity){
		sessionFactory.getCurrentSession().delete(solvedTicketEntity);
	}
	
	public void update(SolvedTicketEntity solvedTicketEntity){
		sessionFactory.getCurrentSession().update(solvedTicketEntity);
	}
}

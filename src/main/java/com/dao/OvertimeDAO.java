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
import com.entity.UpdateControlEntity;

@Repository("OvertimeDAO")
public class OvertimeDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<OvertimeEntity> getOvertimes(Integer user_id, Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from OvertimeEntity where user_id=:user_id and month(date)=:month and year(date)=:year");
		query.setParameter("user_id", user_id);
		query.setParameter("month", date.getMonth() + 1);
		query.setParameter("year", calendar.get(Calendar.YEAR));
		List<OvertimeEntity> overtimeList = (List<OvertimeEntity>) query.list();
		return overtimeList;
	}

	public List<OvertimeEntity> getAllOvertimes(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		Query query = sessionFactory.getCurrentSession()
				.createQuery("from OvertimeEntity where month(date)=:month and year(date)=:year");
		query.setParameter("month", date.getMonth() + 1);
		query.setParameter("year", calendar.get(Calendar.YEAR));
		List<OvertimeEntity> overtimeList = (List<OvertimeEntity>) query.list();
		return overtimeList;
	}
	
	public List<OvertimeEntity> getAllOvertimes(){
		List<OvertimeEntity>overtimeList = sessionFactory.getCurrentSession().createCriteria(OvertimeEntity.class).list();
		return overtimeList;
	}

	
	public void create(OvertimeEntity overtimeEntity){
		sessionFactory.getCurrentSession().persist(overtimeEntity);
	}
	
	public void delete(OvertimeEntity overtimeEntity){
		sessionFactory.getCurrentSession().delete(overtimeEntity);
	}
	
	public void update(OvertimeEntity overtimeEntity){
		sessionFactory.getCurrentSession().update(overtimeEntity);
	}
}

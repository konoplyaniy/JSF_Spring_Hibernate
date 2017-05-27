package com.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.UpdateControlDAO;
import com.entity.SolvedTicketEntity;
import com.entity.UpdateControlEntity;

@Service("updateControlService")
public class UpdateControlService {

	@Autowired
	UpdateControlDAO updateControlDAO;

	public UpdateControlDAO getUpdateControlDAO() {
		return updateControlDAO;
	}

	public void setUpdateControlDAO(UpdateControlDAO updateControlDAO) {
		this.updateControlDAO = updateControlDAO;
	}

	@Transactional
	public void delete(UpdateControlEntity updateControlEntity) {
		updateControlDAO.delete(updateControlEntity);
	}

	@Transactional
	public void create(UpdateControlEntity updateControlEntity) {
		updateControlDAO.create(updateControlEntity);
	}

	@Transactional
	public List<UpdateControlEntity> getUpdateControl(Integer user_id, Date date) {
		return updateControlDAO.getSolvedTickets(user_id, date);
	}

	@Transactional
	public List<UpdateControlEntity> getAllUpdateControl(Date date) {
		return updateControlDAO.getAllUsersSolvedTickets(date);
	}
	
	@Transactional
	public List<UpdateControlEntity> getAllUpdateControl() {
		return updateControlDAO.getAllUsersSolvedTickets();
	}
	
	@Transactional
	public void update(UpdateControlEntity updateControlEntity) {
		updateControlDAO.update(updateControlEntity);
	}

}

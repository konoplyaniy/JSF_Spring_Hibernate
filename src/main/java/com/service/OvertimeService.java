package com.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.OvertimeDAO;
import com.entity.OvertimeEntity;

@Service("overtimeService")
public class OvertimeService {

	@Autowired
	OvertimeDAO overtimeDAO;

	public OvertimeDAO getOvertimeDAO() {
		return overtimeDAO;
	}

	public void setOvertimeDAO(OvertimeDAO overtimeDAO) {
		this.overtimeDAO = overtimeDAO;
	}

	@Transactional
	public void create(OvertimeEntity overtimeEntity) {
		overtimeDAO.create(overtimeEntity);
	}

	@Transactional
	public void delete(OvertimeEntity overtimeEntity) {
		overtimeDAO.delete(overtimeEntity);
	}

	@Transactional
	public void update(OvertimeEntity overtimeEntity) {
		overtimeDAO.update(overtimeEntity);
	}

	@Transactional
	public List<OvertimeEntity> getUserEntities(Integer user_id, Date date) {
		return overtimeDAO.getOvertimes(user_id, date);
	}

	@Transactional
	public List<OvertimeEntity> getAllEntities(Date date) {
		return overtimeDAO.getAllOvertimes(date);
	}
	
	@Transactional
	public List<OvertimeEntity> getAllEntities() {
		return overtimeDAO.getAllOvertimes();
	}

}

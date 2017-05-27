package com.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.SolvedTicketDAO;
import com.entity.CreateTicketEntity;
import com.entity.SolvedTicketEntity;

@Service("solvedTicketService")
public class SolvedTicketService {

	@Autowired
	SolvedTicketDAO solvedTicketDAO;

	public SolvedTicketDAO getSolvedTicketDAO() {
		return solvedTicketDAO;
	}

	public void setSolvedTicketDAO(SolvedTicketDAO solvedTicketDAO) {
		this.solvedTicketDAO = solvedTicketDAO;
	}

	@Transactional
	public void solvedTicket(SolvedTicketEntity solvedTicketEntity) {
		solvedTicketDAO.create(solvedTicketEntity);
	}

	@Transactional
	public void deleteTicket(SolvedTicketEntity solvedTicketEntity) {
		solvedTicketDAO.delete(solvedTicketEntity);
	}

	@Transactional
	public List<SolvedTicketEntity> getSolvedTickets(Integer user_id, Date date) {
		return solvedTicketDAO.getSolvedTickets(user_id, date);
	}
	
	@Transactional
	public List<SolvedTicketEntity>getAllSolvedTickets(Date date){
		return solvedTicketDAO.getAllUsersSolvedTickets(date);
	}
	
	@Transactional
	public List<SolvedTicketEntity>getAllSolvedTickets(){
		return solvedTicketDAO.getAllUsersSolvedTickets();
	}
	
	@Transactional
	public void update(SolvedTicketEntity solvedTicketEntity) {
		solvedTicketDAO.update(solvedTicketEntity);
	}

}

package com.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CreateTicketDAO;
import com.entity.CreateTicketEntity;

@Service("createTicketService")
public class CreateTicketService {

	@Autowired
	CreateTicketDAO createTicketDAO;

	public CreateTicketDAO getCreateTicketDAO() {
		return createTicketDAO;
	}

	public void setCreateTicketDAO(CreateTicketDAO createTicketDAO) {
		this.createTicketDAO = createTicketDAO;
	}

	@Transactional
	public void createTicket(CreateTicketEntity createTicketEntity) {
		createTicketDAO.createTicket(createTicketEntity);
	}

	@Transactional
	public void deleteTicket(CreateTicketEntity createTicketEntity) {
		createTicketDAO.delete(createTicketEntity);
	}

	@Transactional
	public void updateTicket(CreateTicketEntity createTicketEntity) {
		createTicketDAO.update(createTicketEntity);
	}

	@Transactional
	public List<CreateTicketEntity> getTickets(Integer id, Date date) {
		return createTicketDAO.getCreateTickets(id, date);
	}

	@Transactional
	public List<CreateTicketEntity> getAllTickets(Date date) {
		return createTicketDAO.getAllUsersCreateTickets(date);
	}
	
	@Transactional
	public List<CreateTicketEntity> getAllTickets() {
		return createTicketDAO.getAllUsersCreateTickets();
	}
}

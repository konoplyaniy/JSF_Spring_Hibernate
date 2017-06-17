package com.service;

import com.dao.TicketDao;
import com.entity.TicketEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Service("ticketService")
public class TicketService {
    @Autowired
    TicketDao ticketDao;

    public TicketService (){}

    public TicketDao getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Transactional
    public ArrayList<TicketEntity> getTicketsByDateUserId(int userId, Date date){
        return ticketDao.getTicketsByDateUserId(userId, date);
    }

    @Transactional
    public ArrayList<TicketEntity> getAllUsersTicketsByMonth(Date date){
        return ticketDao.getAllUsersTicketsByMonth(date);
    }

    @Transactional
    public ArrayList<TicketEntity> getCreatedTicketsByUserId(int userId, Date date){
        return ticketDao.getCreatedTicketsByUserId(userId, date);
    }

    @Transactional
    public ArrayList<TicketEntity> getClosedTicketsByDateUserId(int userId, Date date){
        return ticketDao.getClosedTicketsByDateUserId(userId, date);
    }

    @Transactional
    public void createTicket(TicketEntity ticketEntity){
        ticketDao.createTicket(ticketEntity);
    }

    @Transactional
    public void deleteTicket(TicketEntity ticketEntity){
        ticketDao.deleteTicket(ticketEntity);
    }

    @Transactional
    public void updateTcket(TicketEntity ticketEntity){
        ticketDao.updateTicket(ticketEntity);
    }


}

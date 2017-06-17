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
@Service("TicketService")
public class TicketService {
    @Autowired
    TicketDao ticketDao;

    public TicketDao getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Transactional
    public ArrayList<TicketEntity> getTicketsByDateUserId(Integer userId, Date date){
        return ticketDao.getTicketsByDateUserId(userId, date);
    }

    @Transactional
    public ArrayList<TicketEntity> getAllUsersTicketsByDate(Date date){
        return ticketDao.getAllUsersTicketsByDate(date);
    }

    @Transactional
    public ArrayList<TicketEntity> getCreatedTicketsByUserId(Integer userId, Date date){
        return ticketDao.getCreatedTicketsByUserId(userId, date);
    }

    @Transactional
    public ArrayList<TicketEntity> getClosedTicketsByDatUserId(Integer userId, Date date){
        return ticketDao.getClosedTicketsByDatUserId(userId, date);
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

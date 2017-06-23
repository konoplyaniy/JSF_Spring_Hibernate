package com.dao;

import com.entity.TicketEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Repository("TicketDAO")
public class TicketDao {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ArrayList<TicketEntity> getUserTicketsByDate(int user_id, Date date) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(
                        "from TicketEntity where open_user_id=:user_id and month(date)=month(:date) and year(date)=year(:date)");
        query.setParameter("user_id", user_id);
        query.setParameter("date", date);
        ArrayList<TicketEntity> createList = (ArrayList<TicketEntity>) query.list();
        return createList;
    }


    public ArrayList<TicketEntity> getUserCreatedTicketsByDate(int user_id, Date date) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(
                        "from TicketEntity where open_user_id=:user_id and month(date)=month(:date) and year(date)=year(:date)");
        query.setParameter("user_id", user_id);
        query.setParameter("date", date);
        ArrayList<TicketEntity> createList = (ArrayList<TicketEntity>) query.list();
        System.out.println("result list size" + createList.size());
        return createList;
    }

    /**
     * @param user_id
     * @param date
     * @return closed tickets by month from date
     */
    public ArrayList<TicketEntity> getUserClosedTicketsByDate(int user_id, Date date) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(
                        "from TicketEntity where close_user_id=:user_id and month(date)=month(:date) and year(date)=year(:date)");
        query.setParameter("user_id", user_id);
        query.setParameter("date", date);
        ArrayList<TicketEntity> createList = (ArrayList<TicketEntity>) query.list();
        return createList;
    }

    public ArrayList<TicketEntity> getAllUsersTicketsByMonth(Date date) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from TicketEntity where month(date)=month(:date) and year(date)=year(:date)");
        query.setParameter("date", date);
        ArrayList<TicketEntity> createList = (ArrayList<TicketEntity>) query.list();
        return createList;
    }

    //TODO colo method!
   /* public ArrayList<TicketEntity> getAllUsersCreateTickets() {
        ArrayList<TicketEntity> createList = (ArrayList<TicketEntity>) sessionFactory.getCurrentSession().createCriteria(TicketEntity.class).list();
        return createList;
    }*/

    public void createTicket(TicketEntity ticketEntity) {
        sessionFactory.getCurrentSession().persist(ticketEntity);
    }

    public void deleteTicket(TicketEntity ticketEntity) {
        sessionFactory.getCurrentSession().delete(ticketEntity);
    }

    public void updateTicket(TicketEntity ticketEntity) {
        sessionFactory.getCurrentSession().update(ticketEntity);
    }
}

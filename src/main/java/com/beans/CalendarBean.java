package com.beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@SessionScoped
public class CalendarBean implements Serializable {

	private Date date = new Date();
	private Date currentDate = new Date();
	private FacesMessage message;
	private Calendar calendar;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	
	
	/**Method for identify current month
	 * @return true if calendar date equals current date
	 */
	public boolean itsCurrentMonth() {
		Calendar calendarDate = Calendar.getInstance();
		Calendar calendarCurrentDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarCurrentDate.setTime(currentDate);
		if (calendarDate.get(Calendar.MONTH) == calendarCurrentDate.get(Calendar.MONTH)
				&& calendarDate.get(Calendar.YEAR) == calendarCurrentDate.get(Calendar.YEAR)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method for choose next month and display info message
	 */
	public void nextMonth() {
		if (itsCurrentMonth()) {
			date = currentDate;
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, 1);
			date = c.getTime();
		}
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Select month ",
				calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " "
						+ calendar.get(Calendar.YEAR));
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	/**
	 * Method for choose previous month and display info message
	 */
	public void previousMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		date = c.getTime();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Select month ",
				calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " "
						+ calendar.get(Calendar.YEAR));
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}

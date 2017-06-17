package com.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.entity.OvertimeEntity;
import com.entity.UserEntity;
import com.service.OvertimeService;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.primefaces.event.SelectEvent;

@ManagedBean
@SessionScoped
public class OvertimeBean implements Serializable {

	private OvertimeEntity overtimeEntity;

	private Timestamp overtime_form_date_from;
	private Date overtime_form_date_to;
	private String overtime_form_project;
	private OvertimeEntity overtimePopup = new OvertimeEntity();
	private boolean approvementAll;
	private boolean approveSingle;
	private FacesMessage message;

	@ManagedProperty(value = "#{userService}")
	com.service.UserService userService;

	@ManagedProperty(value = "#{overtimeService}")
	OvertimeService overtimeService;

	@ManagedProperty(value = "#{loginBean}")
	LoginBean loginBean;

	@ManagedProperty(value = "#{calendarBean}")
	CalendarBean calendarBean;

	@ManagedProperty(value = "#{mail}")
	private Mail mail;

	public CalendarBean getCalendarBean() {
		return calendarBean;
	}

	public void setCalendarBean(CalendarBean calendarBean) {
		this.calendarBean = calendarBean;
	}

	public OvertimeService getOvertimeService() {
		return overtimeService;
	}

	public void setOvertimeService(OvertimeService overtimeService) {
		this.overtimeService = overtimeService;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public OvertimeEntity getOvertimeEntity() {
		return overtimeEntity;
	}

	public void setOvertimeEntity(OvertimeEntity overtimeEntity) {
		overtimeEntity = overtimeEntity;
	}

	public Date getOvertime_form_date_from() {
		return overtime_form_date_from;
	}

	public void setOvertime_form_date_from(Date overtime_form_date_from) {
		this.overtime_form_date_from = (Timestamp) overtime_form_date_from;
	}

	public Date getOvertime_form_date_to() {
		return overtime_form_date_to;
	}

	public void setOvertime_form_date_to(Date overtime_form_date_to) {
		this.overtime_form_date_to = overtime_form_date_to;
	}

	public String getOvertime_form_project() {
		return overtime_form_project;
	}

	public void setOvertime_form_project(String overtime_form_project) {
		this.overtime_form_project = overtime_form_project;
	}

	public OvertimeEntity getOvertimePopup() {
		return overtimePopup;
	}

	public void setOvertimePopup(OvertimeEntity overtimePopup) {
		this.overtimePopup = overtimePopup;
	}

	public boolean isApprovementAll() {
		return approvementAll;
	}

	public void setApprovementAll(boolean approvementAll) {
		this.approvementAll = approvementAll;
	}

	public boolean isApproveSingle() {
		return approveSingle;
	}

	public void setApproveSingle(boolean approveSingle) {
		this.approveSingle = approveSingle;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public com.service.UserService getUserService() {
		return userService;
	}

	public void setUserService(com.service.UserService userService) {
		this.userService = userService;
	}

	public boolean intToBoolean(int number) {
		if (number == 1) {
			return true;
		} else
			return false;
	}

	public String getTotalHours() {
		ArrayList<OvertimeEntity> overtimes = null;
		;
		if (loginBean.getUserRole().equals("admin")) {
			overtimes = overtimeService.getAllOvertimes(calendarBean.getDate());
		} else if (loginBean.getUserRole().equals("user")) {
			overtimes = overtimeService.getOvertimesByDateUserId(loginBean.getUser_id(), calendarBean.getDate());
		}
		Date total_hours = null;
		//TODO Need to inspect this
		for (OvertimeEntity overtime : overtimes) {
			total_hours = overtime.getPeriod();
		}
		Period p = new Period(total_hours);
		String totalMessage = "";
		long hours = p.getHours();
		long minutes = p.getMinutes();
		return totalMessage + hours + "h" + minutes + "m";
	}

	public String getDifferentBetweenDate(Date date_from, Date date_to) {
		DateTime dateFrom = new DateTime(date_from);
		DateTime dateTo = new DateTime(date_to);
		Period p = new Period(dateFrom, dateTo);
		long days = p.getDays();
		long hours = p.getHours();
		long minutes = p.getMinutes();
		String message = "";
		if (days != 0) {
			message = message + days + "d";
		}
		return message + hours + "h" + minutes + "m";
	}

	public List<OvertimeEntity> getOvertimes() {
		if (loginBean.getUserRole().equals("admin")) {
			return overtimeService.getAllOvertimes(calendarBean.getDate());
		} else if (loginBean.getUserRole().equals("user")) {
			return overtimeService.getOvertimesByDateUserId(loginBean.getUser_id(), calendarBean.getDate());
		} else {
			return null;
		}
	}

	public void onRowSelect(SelectEvent event) {
		overtimePopup = (OvertimeEntity) event.getObject();
	}

	public void create() {
		OvertimeEntity overtimeEntity = new OvertimeEntity();
		overtimeEntity.setUser_id(loginBean.getUser_id());
		overtimeEntity.setPeriod(overtime_form_date_from);
		overtimeEntity.setProject(overtime_form_project);
		overtimeEntity.setApprovement(0);
		overtimeEntity.setDate(calendarBean.getDate());
		overtimeService.createOvertime(overtimeEntity);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Overtime created", "From: " + overtime_form_date_from
				+ "\n " + "To: " + overtime_form_date_to + "\n" + "Project: " + overtime_form_project);
		FacesContext.getCurrentInstance().addMessage(null, message);
		for (UserEntity userEntity : userService.getAllUsers()) {
			if (userEntity.getRole().equals("admin")) {
				mail.sendThreadEmail("Create overtime",
						"Hi ! \n" + "User " + loginBean.getUser_first_name() + " " + loginBean.getUser_last_name()
								+ " create overtime for date \n" + "Date from: " + overtime_form_date_to + "\n"
								+ "Date to: " + overtime_form_date_to + "\n" + "Project name: " + overtime_form_project
								+ "\n" + "Pls approve !",
						userEntity.getEmail());
			}
		}
		overtime_form_date_from = null;
		overtime_form_date_to = null;
		overtime_form_project = "";
	}

	//TODO Need to inspect
	public void update(OvertimeEntity overtimeEntity) {
		if (overtimeEntity.getPeriod() == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Overtime can't be empty",
					overtimeEntity.getProject());
		} else {
			overtimeService.updateOvertime(overtimeEntity);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Overtime updated", overtimeEntity.getProject());
//			//TODO Need to inspect
			/*for (UserEntity userEntity : userService.getAllUsers()) {
				if (userEntity.getRole().equals("admin")) {
					mail.sendThreadEmail("Update overtime",
							"Hi ! \n" + "User " + overtimeEntity.getUserEntity().getUser_first_name() + " "
									+ overtimeEntity.getUserEntity().getUser_last_name()
									+ " update overtime for date \n" + "Date from: " + overtimeEntity.getDate_from()
									+ "\n" + "Date to: " + overtimeEntity.getDate_to() + "\n" + "Project name: "
									+ overtimeEntity.getProject() + "\n" + "Pls approve !",
							userEntity.getUser_email());
				}
			}*/
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void delete(OvertimeEntity overtimeEntity) {
		overtimeService.deleteteOvertime(overtimeEntity);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Overtime deleted", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void singleApprove() {
		overtimePopup.setApprovement(1);
		overtimeService.updateOvertime(overtimePopup);
		//TODO need to inspect
//		message = new FacesMessage(FacesMessage.SEVERITY_INFO,
//				"Overtime for " + overtimePopup.getUserEntity().getUser_first_name() + " approved",
//				overtimePopup.getDate_from() + "-" + overtimePopup.getDate_to());
//		FacesContext.getCurrentInstance().addMessage(null, message);
//		mail.sendThreadEmail("Approve overtime",
//				"Hi ! \n" + "Approve overtime for user " + overtimePopup.getUserEntity().getUser_first_name() + " "
//						+ overtimePopup.getUserEntity().getUser_last_name() + " \n" + "Date from: "
//						+ overtimePopup.getDate_from() + "\n" + "Date to: " + overtimePopup.getDate_to() + "\n"
//						+ "Project name: " + overtimePopup.getProject(),
//				overtimePopup.getUserEntity().getUser_email());
	}

	public void singleDisapprove() {
		overtimePopup.setApprovement(0);
		overtimeService.updateOvertime(overtimePopup);
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
//				"Overtime for " + overtimePopup.getUserEntity().getUser_first_name() + " disapprove",
//				overtimePopup.getDate_from() + "-" + overtimePopup.getDate_to());
//		FacesContext.getCurrentInstance().addMessage(null, message);
//		mail.sendThreadEmail("Dissaprove overtime",
//				"Hi ! \n" + "Approve overtime for user " + overtimePopup.getUserEntity().getUser_first_name() + " "
//						+ overtimePopup.getUserEntity().getUser_last_name() + " \n" + "Date from: "
//						+ overtimePopup.getDate_from() + "\n" + "Date to: " + overtimePopup.getDate_to() + "\n"
//						+ "Project name: " + overtimePopup.getProject(),
//				overtimePopup.getUserEntity().getUser_email());
	}

	public void switchApprovement() {
		if (approvementAll) {
			List<OvertimeEntity> overtimeEntities = overtimeService.getAllOvertimes(calendarBean.getDate());
			for (OvertimeEntity overtime : overtimeEntities) {
				overtime.setApprovement(1);
				overtimeService.updateOvertime(overtime);
			}
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "All overtimes aproved", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else if (!approvementAll) {
			List<OvertimeEntity> overtimeEntities = overtimeService.getAllOvertimes(calendarBean.getDate());
			for (OvertimeEntity overtime : overtimeEntities) {
				overtime.setApprovement(0);
				overtimeService.updateOvertime(overtime);

			}
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "All overtimes disaproved", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

}

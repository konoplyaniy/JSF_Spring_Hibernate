package com.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

import org.primefaces.context.RequestContext;

import com.entity.CreateTicketEntity;
import com.entity.SolvedTicketEntity;
import com.entity.UpdateControlEntity;
import com.service.CreateTicketService;
import com.service.SolvedTicketService;
import com.service.UpdateControlService;

@ManagedBean
@SessionScoped
public class CreateTicketBean implements Serializable {

	private List<Areas> selectedAreas;
	private Products selectedProduct;
	private FacesMessage message;

	@ManagedProperty(value = "#{createTicketService}")
	CreateTicketService createTicketService;

	@ManagedProperty(value = "#{loginBean}")
	LoginBean loginBean;

	@ManagedProperty(value = "#{calendarBean}")
	CalendarBean calendarBean;

	@ManagedProperty(value = "#{solvedTicketService}")
	SolvedTicketService solvedTicketService;

	@ManagedProperty(value = "#{updateControlService}")
	UpdateControlService updateControlService;

	private List<CreateTicketEntity> deleteTickets;

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public CalendarBean getCalendarBean() {
		return calendarBean;
	}

	public void setCalendarBean(CalendarBean calendarBean) {
		this.calendarBean = calendarBean;
	}

	public CreateTicketService getCreateTicketService() {
		return createTicketService;
	}

	public void setCreateTicketService(CreateTicketService createTicketService) {
		this.createTicketService = createTicketService;
	}

	public SolvedTicketService getSolvedTicketService() {
		return solvedTicketService;
	}

	public void setSolvedTicketService(SolvedTicketService solvedTicketService) {
		this.solvedTicketService = solvedTicketService;
	}

	public UpdateControlService getUpdateControlService() {
		return updateControlService;
	}

	public void setUpdateControlService(UpdateControlService updateControlService) {
		this.updateControlService = updateControlService;
	}

	public enum Products {
		Domains(1), Windows_Hosting(2), Linux_Hosting(3), Email_Hosting(4), Email_Exchange(5), Linux_Servers(
				6), Windows_Servers(7), Custom_Servers(8), Email_Fax(9), Secure_SSL(10), Email_Protect(
						11), Website_Builder(12), Eshop_Builder(13), Email_Marketing(14), Trafic_Booster(
								15), Search_Ads(16), Site_Protection(17), Analytics(18), Web_Design(19), Logo_Design(
										20), Packages(21), Pricing_And_Promos(22), Js_Css_Styles_Help(
												23), Accounts_Inc_Crms(24), Crms(25), Shopping_Cart(26), Dns_Hosting(
														27), Affiliates(28), Advanced_Support(29), Live_Chat(
																30), Payment_And_PayExpress(31), WhoIs(31), Seo(
																		32), Business_Directory(
																				33), Emails_s_Notifications(
																						34), Reseller_Console(35);

		Products(Integer id) {
			this.id = id;
		}

		public Integer id;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
	}

	public enum Areas {
		Front(1), Members(2);

		Areas(Integer id) {
			this.id = id;
		}

		public Integer id;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
	}

	public List<Areas> getSelectedAreas() {
		return selectedAreas;
	}

	public void setSelectedAreas(List<Areas> selectedAreas) {
		this.selectedAreas = selectedAreas;
	}

	public Products getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Products selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public List<Areas> getAvailableAreas() {
		return Arrays.asList(Areas.values());
	}

	public List<Products> getAvailableProducts() {
		return Arrays.asList(Products.values());
	}

	public List<CreateTicketEntity> getDeleteTickets() {
		return deleteTickets;
	}

	public void setDeleteTickets(List<CreateTicketEntity> deleteTickets) {
		this.deleteTickets = deleteTickets;
	}

	/**
	 * Method for create new ticket and save it in data base
	 */
	public void createTicket() {
		Integer front_area = 0;
		Integer members_area = 0;
		String areaMessage = "";
		if (selectedAreas.size() != 0 && selectedProduct.name().length() != 0) {
			for (Areas area : selectedAreas) {
				if (area.name().equals("Front")) {
					front_area = 1;
					areaMessage = areaMessage + " Front area \n";
				}
				if (area.name().equals("Members")) {
					members_area = 1;
					areaMessage = areaMessage + "Members area \n";
				}
			}
			CreateTicketEntity createTicketEntity = new CreateTicketEntity();
			createTicketEntity.setUser_id(loginBean.getUser_id());
			createTicketEntity.setDate(calendarBean.getDate());
			createTicketEntity.setFront_area(front_area);
			createTicketEntity.setMembers_area(members_area);
			createTicketEntity.setProduct(selectedProduct.name());
			createTicketService.createTicket(createTicketEntity);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ticket created",
					selectedProduct.name() + "\n" + areaMessage);
		} else if (selectedAreas.size() == 0 || selectedProduct.name().length() == 0) {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validation error", "Choose product and area");
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Method for get List of created tickets from data base
	 * 
	 * @return List of created tickets
	 */
	public List<CreateTicketEntity> getCreatedTickets() {
		if (loginBean.getUserRole().equals("admin")) {
			return createTicketService.getAllTickets(calendarBean.getDate());
		} else if (loginBean.getUserRole().equals("user")) {
			return createTicketService.getTickets(loginBean.getUser_id(), calendarBean.getDate());
		} else {
			return null;
		}
	}

	/**
	 * Delete choosen tickets from popup
	 */
	public void deleteTickets() {
		String msg = "";
		for (CreateTicketEntity delete : deleteTickets) {
			createTicketService.deleteTicket(delete);
			msg = msg + delete.getProduct() + ",";
		}
		message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Delete tickets :", msg);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * method for abort solved tickets
	 */
	public void deleteSolvedTickets() {
		String msg = "";
		List<SolvedTicketEntity> solvedList = null;
		if (loginBean.getUserRole().equals("admin")) {
			solvedList = solvedTicketService.getAllSolvedTickets(calendarBean.getDate());
		} else if (loginBean.getUserRole().equals("user")) {
			solvedList = solvedTicketService.getSolvedTickets(loginBean.getUser_id(), calendarBean.getDate());
		}
		SolvedTicketEntity solvedTicketEntity = null;
		for (SolvedTicketEntity solved : solvedList) {
			solvedTicketEntity = solved;
		}
		solvedTicketService.deleteTicket(solvedTicketEntity);
		message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Abort solved ticket", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Method for abort update control
	 */
	public void deleteUpdateControl() {
		String msg = "";
		List<UpdateControlEntity> updateList = null;
		if (loginBean.getUserRole().equals("admin")) {
			updateList = updateControlService.getAllUpdateControl(calendarBean.getDate());
		} else if (loginBean.getUserRole().equals("user")) {
			updateList = updateControlService.getUpdateControl(loginBean.getUser_id(), calendarBean.getDate());
		}
		UpdateControlEntity updateControlEntity = null;
		for (UpdateControlEntity update : updateList) {
			updateControlEntity = update;
		}
		updateControlService.delete(updateControlEntity);
		message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Abort update control", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Method for solved ticket
	 */
	public void createSolvedTicket() {
		SolvedTicketEntity solvedTicketEntity = new SolvedTicketEntity();
		solvedTicketEntity.setUser_id(loginBean.getUser_id());
		solvedTicketEntity.setDate(calendarBean.getDate());
		solvedTicketService.solvedTicket(solvedTicketEntity);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solved ticket", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Method for create update control
	 */
	public void createUpdateControl() {
		UpdateControlEntity updateControlEntity = new UpdateControlEntity();
		updateControlEntity.setUser_id(loginBean.getUser_id());
		updateControlEntity.setDate(calendarBean.getDate());
		updateControlService.create(updateControlEntity);
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Create update control", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Method for get update control count for admins and users
	 * 
	 * @return update control count
	 */
	public Integer getUpdateControlCount() {
		if (loginBean.getUserRole().equals("admin")) {
			return updateControlService.getAllUpdateControl(calendarBean.getDate()).size();
		} else if (loginBean.getUserRole().equals("user")) {
			return updateControlService.getUpdateControl(loginBean.getUser_id(), calendarBean.getDate()).size();
		} else {
			return 0;
		}
	}

	/**
	 * Method for get solved tickets count for admins and users
	 * 
	 * @return solved tickets count
	 */
	public Integer getSolvedTicketsCount() {
		if (loginBean.getUserRole().equals("admin")) {
			return solvedTicketService.getAllSolvedTickets(calendarBean.getDate()).size();
		} else if (loginBean.getUserRole().equals("user")) {
			return solvedTicketService.getSolvedTickets(loginBean.getUser_id(), calendarBean.getDate()).size();
		} else {
			return 0;
		}
	}

	/**
	 * Method for get created tickets count for admins and users
	 * 
	 * @return create tickets count
	 */
	public Integer getCreatedTicketsCount() {
		if (loginBean.getUserRole().equals("admin")) {
			return createTicketService.getAllTickets(calendarBean.getDate()).size();
		} else if (loginBean.getUserRole().equals("user")) {
			return createTicketService.getTickets(loginBean.getUser_id(), calendarBean.getDate()).size();
		} else {
			return 0;
		}
	}

	/**
	 * Method for count solved tickets by user id for statistic table
	 * 
	 * @param user_id
	 * @return count solved tickets
	 */
	public Integer getSolvedTicketsId(Integer userId) {
		return solvedTicketService.getSolvedTickets(userId, calendarBean.getDate()).size();
	}

	/**
	 * Method for count update control by user id for statistic table
	 * 
	 * @param user_id
	 * @return count update control
	 */
	public Integer getUpdateControlId(Integer userId) {
		return updateControlService.getUpdateControl(userId, calendarBean.getDate()).size();
	}

	/**
	 * Method for count created tickets by user id for statistic table
	 * 
	 * @param user_id
	 * @return count created tickets
	 */
	public Integer getCreatedTicketsId(Integer userId) {
		return createTicketService.getTickets(userId, calendarBean.getDate()).size();
	}

}

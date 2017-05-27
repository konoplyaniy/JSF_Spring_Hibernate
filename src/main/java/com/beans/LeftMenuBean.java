package com.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class LeftMenuBean implements Serializable {

	private String showView = "mainDashboard.xhtml";

	public String getShowView() {
		return showView;
	}

	public void setShowView(String showView) {
		this.showView = showView;
	}

	public void clickOnmainDashboard() {
		showView = "mainDashboard.xhtml";
	}

	public void clickOnEditProfiles() {
		showView = "editUserPage.xhtml";
	}
}

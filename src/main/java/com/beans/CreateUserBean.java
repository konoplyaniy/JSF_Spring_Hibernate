package com.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import com.entity.UserEntity;
import com.utils.SupportUtils;

@ManagedBean
@RequestScoped
public class CreateUserBean implements Serializable {

	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private FacesMessage message;

	@ManagedProperty(value = "#{userService}")
	com.service.UserService userService;

	@ManagedProperty(value = "#{mail}")
	private Mail mail;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public com.service.UserService getUserService() {
		return userService;
	}

	public void setUserService(com.service.UserService userService) {
		this.userService = userService;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void createUser() {
		if (firstName.length()!=0 && lastName.length()!=0 && email.length()!=0) {
			String username = SupportUtils.getUsernameFromEmail(email);
			String password = SupportUtils.generatePassword();
			UserEntity userEntity = new UserEntity();
			userEntity.setLogin(username);
			userEntity.setPassword(SupportUtils.MD5(password));
			userEntity.setFirst_name(firstName);
			userEntity.setLast_name(lastName);
			userEntity.setRole("user");
			userEntity.setEmail(email);
			userService.createUser(userEntity);
			mail.sendThreadEmail("Dreamscape.QA notification", "Hi ! " + System.lineSeparator() +
					firstName + " " + lastName + System.lineSeparator() +
					"Congrats! You was successfully registered on Dreamscape.QA portal" + System.lineSeparator() +
					"Your credentials:" + System.lineSeparator() +
					"Login: " + username + System.lineSeparator() +
					"Password: " + password + System.lineSeparator() +
					System.lineSeparator() +
					"Dreamscape.QA portal: selenium-au.internal.dremscapeneworks.com"
					, email);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "User created", firstName + " " + lastName);
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validation error", "Fields should not be empty");
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}

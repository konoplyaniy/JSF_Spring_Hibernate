package com.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

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
		if (!firstName.equals("") && !lastName.equals("") && email.equals("")) {
			String username = SupportUtils.getUsernameFromEmail(email);
			String password = SupportUtils.generatePassword();
			UserEntity userEntity = new UserEntity();
			userEntity.setUsername(username);
			userEntity.setPassword(SupportUtils.MD5(password));
			userEntity.setUser_first_name(firstName);
			userEntity.setUser_last_name(lastName);
			userEntity.setUser_role("user");
			userEntity.setUser_email(email);
			userService.createUser(userEntity);
			mail.sendThreadEmail("Create user for Dreamscape.QA", "Hi ! " + System.lineSeparator() +
					"Login: " + username + System.lineSeparator() + "Password: " + password, email);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "User created", firstName + " " + lastName);
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validation error", "Fields should not be empty");
		}
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}

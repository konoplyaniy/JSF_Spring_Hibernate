package com.beans;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.entity.UserEntity;
import com.utils.SessionUtils;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private String username;
	private String password;
	private UserEntity user;

	@ManagedProperty(value = "#{userService}")
	com.service.UserService userService;

	public com.service.UserService getUserService() {
		return userService;
	}

	public void setUserService(com.service.UserService userService) {
		this.userService = userService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return user.getUser_role();
	}

	public String getUser_first_name() {
		return user.getUser_first_name();
	}

	public String getUser_last_name() {
		return user.getUser_last_name();
	}

	public Integer getUser_id() {
		return user.getUser_id();
	}
	
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	/** Method for render avatar from data base
	 * @return user avatat image
	 */
	public StreamedContent getUserAvatar() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String userId = context.getExternalContext().getRequestParameterMap().get("userId");
			UserEntity user = userService.getUser(Integer.valueOf(userId));
			return new DefaultStreamedContent(new ByteArrayInputStream(user.getUser_avatar()));
		}
	}

	/** Method for login to system
	 * @return User 
	 */
	public String login() {
		UserEntity user = userService.validateUser(username, password);
		if (user != null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", username);
			this.user = user;
			return user.getUser_role();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect username or password", "Please correct data"));
			return null;
		}
	}

	/** Method to logout
	 * logout to enter page
	 */
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Logout from dashboard", "User " + user.getUser_first_name() + " " + user.getUser_last_name()));
		return "logout";
	}

	/** Method for getting all users
	 * @return list of all users in system
	 */
	public List<UserEntity> getAllUsers(){
		if(user.getUser_role().equals("admin")){
		return userService.getAllUsers();
		}else if(user.getUser_role().equals("user")){
			List<UserEntity>users=new ArrayList<>();
			users.add(user);
			return users;
		}else return null;
	}
	

}

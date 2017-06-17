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
		return user.getRole();
	}

	public String getUser_first_name() {
		return user.getFirst_name();
	}

	public String getUser_last_name() {
		return user.getLast_name();
	}

	public int getUser_id() {
		return user.getId();
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
			return new DefaultStreamedContent(new ByteArrayInputStream(user.getAvatar()));
		}
	}

	/** Method for login to system
	 * @return User 
	 */
	public String login() {
		/*if (username.equals("user") && password.equals("user")){
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", username);
			return "user";
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect username or password", "Please correct data"));
			return null;
		}*/

		UserEntity user = userService.validateUser(username, password);
		if (user != null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", username);
			this.user = user;
			return user.getRole();
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
				"Logout from dashboard", "User " + user.getFirst_name() + " " + user.getLast_name()));
		return "logout";
	}

	/** Method for getting all users
	 * @return list of all users in system
	 */
	public List<UserEntity> getAllUsers(){
		if(user.getRole().equals("admin")){
		return userService.getAllUsers();
		}else if(user.getRole().equals("user")){
			List<UserEntity>users=new ArrayList<>();
			users.add(user);
			return users;
		}else return null;
	}
	

}

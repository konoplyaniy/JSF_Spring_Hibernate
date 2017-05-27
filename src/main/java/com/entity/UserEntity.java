package com.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "users", schema = "test")
public class UserEntity implements Serializable {

	private Integer user_id;
	private String username;
	private String password;
	private String user_role;
	private String user_first_name;
	private String user_last_name;
	private String user_email;
	private byte[] user_avatar;

	private Set<OvertimeEntity> overtimeEntities = new HashSet<OvertimeEntity>(0);
	private Set<CreateTicketEntity> createTicketEntities = new HashSet<CreateTicketEntity>(0);

	public UserEntity() {
	}

	public UserEntity(String username, String password, String user_role, String user_first_name, String user_last_name,
			String user_email, byte[] user_avatar) {
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.user_role = user_role;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.user_email = user_email;
		this.user_avatar = user_avatar;
	}

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "user_role")
	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	@Column(name = "user_first_name")
	public String getUser_first_name() {
		return user_first_name;
	}

	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}

	@Column(name = "user_last_name")
	public String getUser_last_name() {
		return user_last_name;
	}

	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}

	@Column(name = "user_email")
	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	@Column(name = "user_avatar")
	@Lob
	public byte[] getUser_avatar() {
		return user_avatar;
	}

	public void setUser_avatar(byte[] user_avatar) {
		this.user_avatar = user_avatar;
	}

	@OneToMany(mappedBy = "userEntity")
	public Set<OvertimeEntity> getOvertimeEntities() {
		return overtimeEntities;
	}

	public void setOvertimeEntities(Set<OvertimeEntity> overtimeEntities) {
		this.overtimeEntities = overtimeEntities;
	}
	
	@OneToMany(mappedBy = "userEntity")
	public Set<CreateTicketEntity> getCreateTicketEntities() {
		return createTicketEntities;
	}

	public void setCreateTicketEntities(Set<CreateTicketEntity> createTicketEntities) {
		this.createTicketEntities = createTicketEntities;
	}

}

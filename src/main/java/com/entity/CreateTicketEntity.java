package com.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "created_tickets", schema = "test")
public class CreateTicketEntity implements Serializable {

	private Integer id;
	private Integer user_id;
	private Date date;
	private Integer front_area;
	private Integer members_area;
	private String product;

	private UserEntity userEntity;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id")
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "front_area")
	public Integer getFront_area() {
		return front_area;
	}

	public void setFront_area(Integer front_area) {
		this.front_area = front_area;
	}

	@Column(name = "members_area")
	public Integer getMembers_area() {
		return members_area;
	}

	public void setMembers_area(Integer members_area) {
		this.members_area = members_area;
	}

	@Column(name = "product")
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	@ManyToOne()
	@JoinColumn(name="user_id",updatable=false,insertable=false)
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

}

package com.test;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dao.CreateTicketDAO;
import com.dao.UserDAO;
import com.entity.UserEntity;

public class HibernateTest {

	private ClassPathXmlApplicationContext context;
	private UserDAO userDAO;
	private CreateTicketDAO createTicketDAO;
	public static final Logger LOGGER = Logger.getLogger(HibernateTest.class);

	@BeforeClass
	public void init() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		userDAO = (UserDAO) context.getBean("userDAO");
		createTicketDAO=(CreateTicketDAO)context.getBean("createTicketDAO");
	}

	@Test
	public void hibernateGetAllUsers() {
		for (UserEntity user : userDAO.getAllUSers()) {
			LOGGER.info("Get user name " + user.getUser_first_name() + " ,sunname " + user.getUser_last_name());
		}
		Assert.assertTrue(userDAO.getAllUSers().size() > 0);
	}

	@Test
	public void hibernateGetUserByID() {

	}

	@Test
	public void hibernateGetNotExistID() {
		
	}

	@Test
	public void creareUserTest() {
		;
	}

	@Test
	public void validateUsernameAndPasswordTest() {

	}

	@Test
	public void deleteUser() {
	;
	}
	

}

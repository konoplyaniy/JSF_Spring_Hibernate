package com.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.utils.SupportUtils;

public class UtilsTest {

    public static final Logger LOGGER = Logger.getLogger(UtilsTest.class);
    private String testPassword;

    @BeforeClass
    public void init() {

    }

    @Test
    public void passwordGeneratorTest() {
        testPassword = SupportUtils.generatePassword();
        LOGGER.info("Generate test password [" + testPassword + "]");
        Assert.assertTrue(testPassword.length() == 8);
    }

    @Test
    public void passwordToMD5() {
        String hashPassword = SupportUtils.MD5("password");
        System.out.println(hashPassword);
        LOGGER.info("Generate hash password [" + hashPassword + "]");
        Assert.assertNotNull(hashPassword);
    }

    @Test
    public void usernameFromEmailTest() {
        String username = SupportUtils.getUsernameFromEmail("Dmitriy.F@gmail.com");
        LOGGER.info("Generate username [" + username + "]");
        Assert.assertEquals(username, "Dmitriy.F");
    }
}

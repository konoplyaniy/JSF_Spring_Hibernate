import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dao.TicketDao;
import com.dao.UserDao;
import com.entity.UserEntity;

public class HibernateTest {

    public static final Logger LOGGER = Logger.getLogger(HibernateTest.class);
    private ClassPathXmlApplicationContext context;
    private UserDao userDAO;
    private TicketDao createTicketDAO;

    @BeforeClass
    public void init() {
        context = new ClassPathXmlApplicationContext("/home/geser/QAEngineerStatistic/WebContent/WEB-INF/applicationContext.xml");
        userDAO = (UserDao) context.getBean("userDao");
        createTicketDAO = (TicketDao) context.getBean("ticketDao");
    }

    @Test
    public void hibernateGetAllUsers() {
        for (UserEntity user : userDAO.getAllUsers()) {
            LOGGER.info("Get user name " + user.getFirst_name() + " ,sunname " + user.getLast_name());
        }
        Assert.assertTrue(userDAO.getAllUsers().size() > 0);
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

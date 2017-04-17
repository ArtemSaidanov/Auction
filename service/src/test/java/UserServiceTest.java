import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.auction.entities.User;
import by.saidanov.auction.util.UserType;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.UserService;
import by.saidanov.utils.AuctionLogger;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class UserServiceTest {

    @Test
    public void checkAuthorizationTest() {
        String invalidLogin = "invalidLogin";
        String invalidPassword = "invalidPassword";
        String validLogin = "Art";
        String validPassword = "1234";

        try {
            boolean invalidData = UserService.getInstance().checkUserAuthorization(invalidLogin, invalidPassword);
            boolean validData = UserService.getInstance().checkUserAuthorization(validLogin, validPassword);
            Assert.assertEquals(false, invalidData);
            Assert.assertEquals(true, validData);
        } catch (ServiceException e) {
            HibernateUtil.getHibernateUtil().closeSession();
            e.printStackTrace();
        }
    }

    @Test
    public void getUserByLoginTest() {
        String login = "Art";
        User expectedUser = User.builder()
                                .id(1)
                                .login(login)
                                .password("1234")
                                .firstName("Artem")
                                .lastName("Saidanov")
                                .accessLevel(0)
                                .build();

        try {
            User user = UserService.getInstance().getUserByLogin(login);
            Assert.assertEquals(expectedUser, user);
            User wrongUser = UserService.getInstance().getUserByLogin("");
            Assert.assertNotEquals(expectedUser, wrongUser);
        } catch (ServiceException e) {
            HibernateUtil.getHibernateUtil().closeSession();
            e.printStackTrace();
        }
    }

    @Test
    public void checkIsUserNewTest() {
        String login = "Art";
        User notNewUser = User.builder()
                                .login(login)
                                .build();
        User newUser = User.builder()
                                .login("NewUser")
                                .build();

        try {
            boolean notNewU = UserService.getInstance().checkIsUserNew(notNewUser);
            Assert.assertEquals(notNewU, false);
            boolean newU = UserService.getInstance().checkIsUserNew(newUser);
            Assert.assertEquals(newU, true);
        } catch (ServiceException e) {
            HibernateUtil.getHibernateUtil().closeSession();
            e.printStackTrace();
        }
    }

    @Test
    public void addUserTest() {
        User testUser = User.builder()
                                .login("testUser")
                                .password("test")
                                .firstName("test")
                                .lastName("test")
                                .accessLevel(0)
                                .build();
        Account testAccount = Account.builder()
                                .amountOfMoney(10000)
                                .build();

        try {
            UserService.getInstance().add(testUser, testAccount);
            User actualUser = UserService.getInstance().getUserByLogin("testUser");
            UserService.getInstance().delete(actualUser);
            Assert.assertEquals(testUser, actualUser);
        } catch (ServiceException e) {
            HibernateUtil.getHibernateUtil().closeSession();
            AuctionLogger.getInstance().log(getClass(), e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void checkAccessLevel() {
        User client = User.builder().accessLevel(0).build();
        User admin = User.builder().accessLevel(1).build();
        UserType clientT = UserService.getInstance().checkAccessLevel(client);
        Assert.assertEquals(UserType.CLIENT, clientT);
        UserType adminT = UserService.getInstance().checkAccessLevel(admin);
        Assert.assertEquals(UserType.ADMIN, adminT);
    }

//    @Ignore
//    @Test
//    public void deleteTest() {
//        User testUser = User.builder()
//                                .login("test1312")
//                                .password("test")
//                                .firstName("test")
//                                .lastName("test")
//                                .accessLevel(0)
//                                .build();
//        Account testAccount = Account.builder()
//                                .amountOfMoney(10000)
//                                .build();
//        try (Connection connection = PoolManager.getDataSource().getConnection()) {
//            UserService.getInstance().add(testUser, testAccount);
//            int maxId = UserDAO.getInstance().getMaxId(connection);
//            testUser.setId(maxId);
//            UserService.getInstance().delete(testUser);
//            User deletedUser = UserService.getInstance().getUserByLogin(testUser.getLogin());
//            Assert.assertEquals(null, deletedUser);
//        } catch (SQLException | ServiceException | DaoException e) {
//            e.printStackTrace();
//        }
//    }
}
